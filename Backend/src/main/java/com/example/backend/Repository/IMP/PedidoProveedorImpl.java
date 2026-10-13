package com.example.backend.Repository.IMP;

import com.example.backend.Model.PedidoProveedor;
import com.example.backend.Repository.Interface.PedidoProveedorInterface;
import com.example.backend.Repository.JPA.PedidoProveedorJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementacion de {@link PedidoProveedorInterface} delegando en {@link PedidoProveedorJPA}.
 */
@Repository
@RequiredArgsConstructor
public class PedidoProveedorImpl implements PedidoProveedorInterface {

    private final PedidoProveedorJPA pedidoProveedorJPA;

    @Override
    public List<PedidoProveedor> findAll() {
        return pedidoProveedorJPA.findAll();
    }

    @Override
    public Optional<PedidoProveedor> findById(Long id) {
        return pedidoProveedorJPA.findById(id);
    }

    @Override
    public PedidoProveedor save(PedidoProveedor pedidoProveedor) {
        return pedidoProveedorJPA.save(pedidoProveedor);
    }

    @Override
    public void deleteById(Long id) {
        pedidoProveedorJPA.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return pedidoProveedorJPA.existsById(id);
    }

    @Override
    public long count() {
        return pedidoProveedorJPA.count();
    }

    @Override
    public Optional<PedidoProveedor> findByNumeroPedido(String numeroPedido) {
        return pedidoProveedorJPA.findByNumeroPedido(numeroPedido);
    }

    @Override
    public List<PedidoProveedor> findByEstado(String estado) {
        return pedidoProveedorJPA.findByEstado(estado);
    }

    @Override
    public List<PedidoProveedor> findByEmpleadoId(Long empleadoId) {
        return pedidoProveedorJPA.findByEmpleadoId(empleadoId);
    }
}