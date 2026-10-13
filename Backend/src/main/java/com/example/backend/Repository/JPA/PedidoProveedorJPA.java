package com.example.backend.Repository.JPA;

import com.example.backend.Model.PedidoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio Spring Data JPA para {@link PedidoProveedor}.
 */
public interface PedidoProveedorJPA extends JpaRepository<PedidoProveedor, Long> {

    Optional<PedidoProveedor> findByNumeroPedido(String numeroPedido);

    List<PedidoProveedor> findByEstado(String estado);

    List<PedidoProveedor> findByEmpleadoId(Long empleadoId);
}