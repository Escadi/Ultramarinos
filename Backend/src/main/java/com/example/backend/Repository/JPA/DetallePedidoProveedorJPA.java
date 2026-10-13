package com.example.backend.Repository.JPA;

import com.example.backend.Model.DetallePedidoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio Spring Data JPA para {@link DetallePedidoProveedor}.
 */
public interface DetallePedidoProveedorJPA extends JpaRepository<DetallePedidoProveedor, Long> {

    List<DetallePedidoProveedor> findByPedidoProveedorId(Long pedidoProveedorId);

    List<DetallePedidoProveedor> findByProductoProveedorId(Long productoProveedorId);
}