package com.example.backend.Service.Service;

import com.example.backend.Model.Venta;
import com.example.backend.Repository.VentaJPA;
import com.example.backend.Service.IMP.VentaServiceImpl;
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
public class VentaService implements VentaServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(VentaService.class);

    private final VentaJPA ventaJPA;

    @Override
    public List<Venta> getAll() {
        logger.info("Obteniendo listado de todas las ventas");
        return ventaJPA.findAll();
    }

    @Override
    public Optional<Venta> getById(Long id) {
        logger.info("Buscando venta por ID: {}", id);
        return ventaJPA.findById(id);
    }

    @Override
    public Venta save(Venta venta) {
        logger.info("Guardando nueva venta con ticket: {}", venta.getNumeroTicket());
        return ventaJPA.save(venta);
    }

    @Override
    public Venta update(Long id, Venta venta) {
        logger.info("Actualizando venta con ID: {}", id);
        Venta existing = ventaJPA.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Venta no encontrada con ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Venta no encontrada con ID: " + id);
                });
        existing.setNumeroTicket(venta.getNumeroTicket());
        existing.setFecha(venta.getFecha());
        existing.setTotal(venta.getTotal());
        existing.setEstado(venta.getEstado());
        existing.setBaseImponible(venta.getBaseImponible());
        existing.setTotalIgic(venta.getTotalIgic());
        existing.setCliente(venta.getCliente());
        existing.setTpv(venta.getTpv());
        existing.setTurnoCaja(venta.getTurnoCaja());
        return ventaJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        logger.info("Eliminando venta con ID: {}", id);
        if (!ventaJPA.existsById(id)) {
            logger.warn("No se puede eliminar, venta no encontrada con ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venta no encontrada con ID: " + id);
        }
        ventaJPA.deleteById(id);
    }
}
