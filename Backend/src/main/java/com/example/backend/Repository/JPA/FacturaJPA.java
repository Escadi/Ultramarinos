package com.example.backend.Repository.JPA;

import com.example.backend.Model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio Spring Data JPA para {@link Factura}.
 */
public interface FacturaJPA extends JpaRepository<Factura, Long> {

    Optional<Factura> findByNumeroFactura(String numeroFactura);

    Optional<Factura> findByVentaId(Long ventaId);

    List<Factura> findByClienteId(Long clienteId);

    List<Factura> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}