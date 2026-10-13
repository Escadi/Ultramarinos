package com.example.backend.Repository.JPA;

import com.example.backend.Model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio Spring Data JPA para {@link Venta}.
 */
public interface VentaJPA extends JpaRepository<Venta, Long> {

    Optional<Venta> findByNumeroTicket(String numeroTicket);

    List<Venta> findByClienteId(Long clienteId);

    List<Venta> findByTpvId(Long tpvId);

    List<Venta> findByTurnoCajaId(Long turnoCajaId);

    List<Venta> findByEstado(String estado);

    List<Venta> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}