package com.example.backend.Service.Service;

import com.example.backend.Model.DetallePedidoProveedor;
import com.example.backend.Repository.DetallePedidoProveedorJPA;
import com.example.backend.Service.IMP.DetallePedidoProveedorServiceImpl;
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
public class DetallePedidoProveedorService implements DetallePedidoProveedorServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(DetallePedidoProveedorService.class);

    private final DetallePedidoProveedorJPA detallePedidoProveedorJPA;

    @Override
    public List<DetallePedidoProveedor> getAll() {
        logger.info("Obteniendo listado de todos los detalles de pedido a proveedor");
        return detallePedidoProveedorJPA.findAll();
    }

    @Override
    public Optional<DetallePedidoProveedor> getById(Long id) {
        logger.info("Buscando detalle de pedido a proveedor por ID: {}", id);
        return detallePedidoProveedorJPA.findById(id);
    }

    @Override
    public List<DetallePedidoProveedor> getByPedidoProveedorId(Long pedidoId) {
        logger.info("Obteniendo detalles del pedido a proveedor con ID: {}", pedidoId);
        return detallePedidoProveedorJPA.findByPedidoProveedorId(pedidoId);
    }

    @Override
    public DetallePedidoProveedor save(DetallePedidoProveedor detalle) {
        logger.info("Guardando detalle de pedido a proveedor");
        return detallePedidoProveedorJPA.save(detalle);
    }

    @Override
    public DetallePedidoProveedor update(Long id, DetallePedidoProveedor detalle) {
        logger.info("Actualizando detalle de pedido a proveedor con ID: {}", id);
        DetallePedidoProveedor existing = detallePedidoProveedorJPA.findById(id)
                .orElseThrow(() -> {
                    logger.warn("DetallePedidoProveedor no encontrado con ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "DetallePedidoProveedor no encontrado con ID: " + id);
                });
        existing.setCantidad(detalle.getCantidad());
        existing.setPrecioUnitario(detalle.getPrecioUnitario());
        existing.setDescuento(detalle.getDescuento());
        existing.setSubtotal(detalle.getSubtotal());
        existing.setPedidoProveedor(detalle.getPedidoProveedor());
        existing.setProductoProveedor(detalle.getProductoProveedor());
        return detallePedidoProveedorJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        logger.info("Eliminando detalle de pedido a proveedor con ID: {}", id);
        if (!detallePedidoProveedorJPA.existsById(id)) {
            logger.warn("No se puede eliminar, DetallePedidoProveedor no encontrado con ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DetallePedidoProveedor no encontrado con ID: " + id);
        }
        detallePedidoProveedorJPA.deleteById(id);
    }
}
