package com.example.backend.Repository.Interface;

import com.example.backend.Model.Venta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio de {@link Venta}.
 */
public interface VentaInterface {

    List<Venta> findAll();

    Optional<Venta> findById(Long id);

    Venta save(Venta venta);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();

    Optional<Venta> findByNumeroTicket(String numeroTicket);

    List<Venta> findByClienteId(Long clienteId);

    List<Venta> findByTpvId(Long tpvId);

    List<Venta> findByTurnoCajaId(Long turnoCajaId);

    List<Venta> findByEstado(String estado);

    List<Venta> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}