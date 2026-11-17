package com.example.backend.Service.Service;

import com.example.backend.Model.DetalleCompra;
import com.example.backend.Repository.DetalleCompraJPA;
import com.example.backend.Service.IMP.DetalleCompraServiceImpl;
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
public class DetalleCompraService implements DetalleCompraServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(DetalleCompraService.class);

    private final DetalleCompraJPA detalleCompraJPA;

    @Override
    public List<DetalleCompra> getAll() {
        logger.info("Obteniendo listado de todos los detalles de compra");
        return detalleCompraJPA.findAll();
    }

    @Override
    public Optional<DetalleCompra> getById(Long id) {
        logger.info("Buscando detalle de compra por ID: {}", id);
        return detalleCompraJPA.findById(id);
    }

    @Override
    public List<DetalleCompra> getByCompraId(Long compraId) {
        logger.info("Obteniendo detalles de compra para la compra ID: {}", compraId);
        return detalleCompraJPA.findByCompraId(compraId);
    }

    @Override
    public DetalleCompra save(DetalleCompra detalleCompra) {
        logger.info("Guardando detalle de compra");
        return detalleCompraJPA.save(detalleCompra);
    }

    @Override
    public DetalleCompra update(Long id, DetalleCompra detalleCompra) {
        logger.info("Actualizando detalle de compra con ID: {}", id);
        DetalleCompra existing = detalleCompraJPA.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Detalle de compra no encontrado con ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "DetalleCompra no encontrado con ID: " + id);
                });
        existing.setCantidad(detalleCompra.getCantidad());
        existing.setPrecioUnitario(detalleCompra.getPrecioUnitario());
        existing.setTipoIgic(detalleCompra.getTipoIgic());
        existing.setDescuento(detalleCompra.getDescuento());
        existing.setSubtotal(detalleCompra.getSubtotal());
        existing.setCompra(detalleCompra.getCompra());
        existing.setProducto(detalleCompra.getProducto());
        return detalleCompraJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        logger.info("Eliminando detalle de compra con ID: {}", id);
        if (!detalleCompraJPA.existsById(id)) {
            logger.warn("No se puede eliminar, detalle de compra no encontrado con ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DetalleCompra no encontrado con ID: " + id);
        }
        detalleCompraJPA.deleteById(id);
    }
}
