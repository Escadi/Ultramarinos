package com.example.backend.Service.Service;

import com.example.backend.Model.Pago;
import com.example.backend.Repository.PagoJPA;
import com.example.backend.Service.IMP.PagoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PagoService implements PagoServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(PagoService.class);

    private final PagoJPA pagoJPA;

    @Override
    public List<Pago> getAll() {
        logger.info("Obteniendo listado de todos los pagos");
        return pagoJPA.findAll();
    }

    @Override
    public Optional<Pago> getById(Long id) {
        logger.info("Buscando pago por ID: {}", id);
        return pagoJPA.findById(id);
    }

    @Override
    public List<Pago> getByVentaId(Long ventaId) {
        logger.info("Obteniendo pagos de la venta con ID: {}", ventaId);
        return pagoJPA.findByVentaId(ventaId);
    }

    @Override
    public Pago save(Pago pago) {
        logger.info("Registrando nuevo pago por importe: {}", pago.getImporte());
        return pagoJPA.save(pago);
    }

    @Override
    public Pago update(Long id, Pago pago) {
        logger.info("Actualizando pago con ID: {}", id);
        Pago existing = pagoJPA.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Pago no encontrado con ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no encontrado con ID: " + id);
                });
        existing.setMetodo(pago.getMetodo());
        existing.setImporte(pago.getImporte());
        existing.setFechaPago(pago.getFechaPago());
        existing.setVenta(pago.getVenta());
        return pagoJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        logger.info("Eliminando pago con ID: {}", id);
        if (!pagoJPA.existsById(id)) {
            logger.warn("No se puede eliminar, pago no encontrado con ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no encontrado con ID: " + id);
        }
        pagoJPA.deleteById(id);
    }
}
