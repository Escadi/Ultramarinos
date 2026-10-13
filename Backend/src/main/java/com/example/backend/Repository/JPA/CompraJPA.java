package com.example.backend.Repository.JPA;

import com.example.backend.Model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio Spring Data JPA para {@link Compra}.
 */
public interface CompraJPA extends JpaRepository<Compra, Long> {

    Optional<Compra> findByNumeroCompra(String numeroCompra);

    List<Compra> findByEstado(String estado);

    List<Compra> findByEmpleadoId(Long empleadoId);

    List<Compra> findByPedidoProveedorId(Long pedidoProveedorId);

    List<Compra> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}