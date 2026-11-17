package com.example.backend.Service.Service;

import com.example.backend.Model.Compra;
import com.example.backend.Repository.CompraJPA;
import com.example.backend.Service.IMP.CompraServiceImpl;
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
public class CompraService implements CompraServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(CompraService.class);

    private final CompraJPA compraJPA;

    @Override
    public List<Compra> getAll() {
        logger.info("Obteniendo listado de todas las compras");
        return compraJPA.findAll();
    }

    @Override
    public Optional<Compra> getById(Long id) {
        logger.info("Buscando compra por ID: {}", id);
        return compraJPA.findById(id);
    }

    @Override
    public Compra save(Compra compra) {
        logger.info("Guardando nueva compra con numero: {}", compra.getNumeroCompra());
        return compraJPA.save(compra);
    }

    @Override
    public Compra update(Long id, Compra compra) {
        logger.info("Actualizando compra con ID: {}", id);
        Compra existing = compraJPA.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Compra no encontrada con ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra no encontrada con ID: " + id);
                });
        existing.setNumeroCompra(compra.getNumeroCompra());
        existing.setFecha(compra.getFecha());
        existing.setTotal(compra.getTotal());
        existing.setEstado(compra.getEstado());
        existing.setPedidoProveedor(compra.getPedidoProveedor());
        existing.setEmpleado(compra.getEmpleado());
        return compraJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        logger.info("Eliminando compra con ID: {}", id);
        if (!compraJPA.existsById(id)) {
            logger.warn("No se puede eliminar, compra no encontrada con ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra no encontrada con ID: " + id);
        }
        compraJPA.deleteById(id);
    }
}
