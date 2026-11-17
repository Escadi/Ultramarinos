package com.example.backend.Service.Service;

import com.example.backend.Model.DetalleVenta;
import com.example.backend.Repository.DetalleVentaJPA;
import com.example.backend.Service.IMP.DetalleVentaServiceImpl;
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
public class DetalleVentaService implements DetalleVentaServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(DetalleVentaService.class);

    private final DetalleVentaJPA detalleVentaJPA;

    @Override
    public List<DetalleVenta> getAll() {
        logger.info("Obteniendo listado de todos los detalles de venta");
        return detalleVentaJPA.findAll();
    }

    @Override
    public Optional<DetalleVenta> getById(Long id) {
        logger.info("Buscando detalle de venta por ID: {}", id);
        return detalleVentaJPA.findById(id);
    }

    @Override
    public List<DetalleVenta> getByVentaId(Long ventaId) {
        logger.info("Obteniendo detalles de venta para la venta ID: {}", ventaId);
        return detalleVentaJPA.findByVentaId(ventaId);
    }

    @Override
    public DetalleVenta save(DetalleVenta detalleVenta) {
        logger.info("Guardando detalle de venta");
        return detalleVentaJPA.save(detalleVenta);
    }

    @Override
    public DetalleVenta update(Long id, DetalleVenta detalleVenta) {
        logger.info("Actualizando detalle de venta con ID: {}", id);
        DetalleVenta existing = detalleVentaJPA.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Detalle de venta no encontrado con ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "DetalleVenta no encontrado con ID: " + id);
                });
        existing.setCantidad(detalleVenta.getCantidad());
        existing.setPrecioUnitario(detalleVenta.getPrecioUnitario());
        existing.setTipoIgic(detalleVenta.getTipoIgic());
        existing.setDescuento(detalleVenta.getDescuento());
        existing.setSubtotal(detalleVenta.getSubtotal());
        existing.setVenta(detalleVenta.getVenta());
        existing.setProducto(detalleVenta.getProducto());
        return detalleVentaJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        logger.info("Eliminando detalle de venta con ID: {}", id);
        if (!detalleVentaJPA.existsById(id)) {
            logger.warn("No se puede eliminar, detalle de venta no encontrado con ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DetalleVenta no encontrado con ID: " + id);
        }
        detalleVentaJPA.deleteById(id);
    }
}
