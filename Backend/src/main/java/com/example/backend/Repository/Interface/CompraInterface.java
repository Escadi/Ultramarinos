package com.example.backend.Repository.Interface;

import com.example.backend.Model.Compra;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio de {@link Compra}.
 */
public interface CompraInterface {

    List<Compra> findAll();

    Optional<Compra> findById(Long id);

    Compra save(Compra compra);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();

    Optional<Compra> findByNumeroCompra(String numeroCompra);

    List<Compra> findByEstado(String estado);

    List<Compra> findByEmpleadoId(Long empleadoId);

    List<Compra> findByPedidoProveedorId(Long pedidoProveedorId);

    List<Compra> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}