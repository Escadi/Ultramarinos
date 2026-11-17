package com.example.backend.Service.Service;

import com.example.backend.Model.MovimientoStock;
import com.example.backend.Repository.MovimientoStockJPA;
import com.example.backend.Service.IMP.MovimientoStockServiceImpl;
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
public class MovimientoStockService implements MovimientoStockServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(MovimientoStockService.class);

    private final MovimientoStockJPA movimientoStockJPA;

    @Override
    public List<MovimientoStock> getAll() {
        logger.info("Obteniendo listado de todos los movimientos de stock");
        return movimientoStockJPA.findAll();
    }

    @Override
    public Optional<MovimientoStock> getById(Long id) {
        logger.info("Buscando movimiento de stock por ID: {}", id);
        return movimientoStockJPA.findById(id);
    }

    @Override
    public List<MovimientoStock> getByProductoId(Long productoId) {
        logger.info("Obteniendo movimientos de stock para el producto ID: {}", productoId);
        return movimientoStockJPA.findByProductoId(productoId);
    }

    @Override
    public MovimientoStock save(MovimientoStock movimientoStock) {
        logger.info("Registrando movimiento de stock: tipo={}, cantidad={}", movimientoStock.getTipo(), movimientoStock.getCantidad());
        return movimientoStockJPA.save(movimientoStock);
    }

    @Override
    public void delete(Long id) {
        logger.info("Eliminando movimiento de stock con ID: {}", id);
        if (!movimientoStockJPA.existsById(id)) {
            logger.warn("No se puede eliminar, movimiento de stock no encontrado con ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "MovimientoStock no encontrado con ID: " + id);
        }
        movimientoStockJPA.deleteById(id);
    }
}
