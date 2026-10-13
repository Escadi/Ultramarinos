package com.example.backend.Repository.IMP;

import com.example.backend.Model.DetallePedidoProveedor;
import com.example.backend.Repository.Interface.DetallePedidoProveedorInterface;
import com.example.backend.Repository.JPA.DetallePedidoProveedorJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementacion de {@link DetallePedidoProveedorInterface} delegando en {@link DetallePedidoProveedorJPA}.
 */
@Repository
@RequiredArgsConstructor
public class DetallePedidoProveedorImpl implements DetallePedidoProveedorInterface {

    private final DetallePedidoProveedorJPA detallePedidoProveedorJPA;

    @Override
    public List<DetallePedidoProveedor> findAll() {
        return detallePedidoProveedorJPA.findAll();
    }

    @Override
    public Optional<DetallePedidoProveedor> findById(Long id) {
        return detallePedidoProveedorJPA.findById(id);
    }

    @Override
    public DetallePedidoProveedor save(DetallePedidoProveedor detallePedidoProveedor) {
        return detallePedidoProveedorJPA.save(detallePedidoProveedor);
    }

    @Override
    public void deleteById(Long id) {
        detallePedidoProveedorJPA.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return detallePedidoProveedorJPA.existsById(id);
    }

    @Override
    public long count() {
        return detallePedidoProveedorJPA.count();
    }

    @Override
    public List<DetallePedidoProveedor> findByPedidoProveedorId(Long pedidoProveedorId) {
        return detallePedidoProveedorJPA.findByPedidoProveedorId(pedidoProveedorId);
    }

    @Override
    public List<DetallePedidoProveedor> findByProductoProveedorId(Long productoProveedorId) {
        return detallePedidoProveedorJPA.findByProductoProveedorId(productoProveedorId);
    }
}