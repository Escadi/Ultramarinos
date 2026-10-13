package com.example.backend.Repository.Interface;

import com.example.backend.Model.DetallePedidoProveedor;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio de {@link DetallePedidoProveedor}.
 */
public interface DetallePedidoProveedorInterface {

    List<DetallePedidoProveedor> findAll();

    Optional<DetallePedidoProveedor> findById(Long id);

    DetallePedidoProveedor save(DetallePedidoProveedor detallePedidoProveedor);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();

    List<DetallePedidoProveedor> findByPedidoProveedorId(Long pedidoProveedorId);

    List<DetallePedidoProveedor> findByProductoProveedorId(Long productoProveedorId);
}