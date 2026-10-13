package com.example.backend.Repository.Interface;

import com.example.backend.Model.Factura;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio de {@link Factura}.
 */
public interface FacturaInterface {

    List<Factura> findAll();

    Optional<Factura> findById(Long id);

    Factura save(Factura factura);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();

    Optional<Factura> findByNumeroFactura(String numeroFactura);

    Optional<Factura> findByVentaId(Long ventaId);

    List<Factura> findByClienteId(Long clienteId);

    List<Factura> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}