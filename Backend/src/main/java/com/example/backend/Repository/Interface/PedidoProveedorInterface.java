package com.example.backend.Repository.Interface;

import com.example.backend.Model.PedidoProveedor;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio de {@link PedidoProveedor}.
 */
public interface PedidoProveedorInterface {

    List<PedidoProveedor> findAll();

    Optional<PedidoProveedor> findById(Long id);

    PedidoProveedor save(PedidoProveedor pedidoProveedor);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();

    Optional<PedidoProveedor> findByNumeroPedido(String numeroPedido);

    List<PedidoProveedor> findByEstado(String estado);

    List<PedidoProveedor> findByEmpleadoId(Long empleadoId);
}