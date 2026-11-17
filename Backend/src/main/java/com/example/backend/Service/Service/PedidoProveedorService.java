package com.example.backend.Service.Service;

import com.example.backend.Model.PedidoProveedor;
import com.example.backend.Repository.PedidoProveedorJPA;
import com.example.backend.Service.IMP.PedidoProveedorServiceImpl;
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
public class PedidoProveedorService implements PedidoProveedorServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(PedidoProveedorService.class);

    private final PedidoProveedorJPA pedidoProveedorJPA;

    @Override
    public List<PedidoProveedor> getAll() {
        logger.info("Obteniendo listado de todos los pedidos a proveedores");
        return pedidoProveedorJPA.findAll();
    }

    @Override
    public Optional<PedidoProveedor> getById(Long id) {
        logger.info("Buscando pedido a proveedor por ID: {}", id);
        return pedidoProveedorJPA.findById(id);
    }

    @Override
    public PedidoProveedor save(PedidoProveedor pedidoProveedor) {
        logger.info("Guardando pedido a proveedor con numero: {}", pedidoProveedor.getNumeroPedido());
        return pedidoProveedorJPA.save(pedidoProveedor);
    }

    @Override
    public PedidoProveedor update(Long id, PedidoProveedor pedidoProveedor) {
        logger.info("Actualizando pedido a proveedor con ID: {}", id);
        PedidoProveedor existing = pedidoProveedorJPA.findById(id)
                .orElseThrow(() -> {
                    logger.warn("PedidoProveedor no encontrado con ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "PedidoProveedor no encontrado con ID: " + id);
                });
        existing.setNumeroPedido(pedidoProveedor.getNumeroPedido());
        existing.setFechaPedido(pedidoProveedor.getFechaPedido());
        existing.setEstado(pedidoProveedor.getEstado());
        existing.setTotalEstimado(pedidoProveedor.getTotalEstimado());
        existing.setEmpleado(pedidoProveedor.getEmpleado());
        return pedidoProveedorJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        logger.info("Eliminando pedido a proveedor con ID: {}", id);
        if (!pedidoProveedorJPA.existsById(id)) {
            logger.warn("No se puede eliminar, PedidoProveedor no encontrado con ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PedidoProveedor no encontrado con ID: " + id);
        }
        pedidoProveedorJPA.deleteById(id);
    }
}
