package com.example.backend.Repository.Interface;

import com.example.backend.Model.MovimientoStock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio de {@link MovimientoStock}.
 */
public interface MovimientoStockInterface {

    List<MovimientoStock> findAll();

    Optional<MovimientoStock> findById(Long id);

    MovimientoStock save(MovimientoStock movimientoStock);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();

    List<MovimientoStock> findByProductoId(Long productoId);

    List<MovimientoStock> findByEmpleadoId(Long empleadoId);

    List<MovimientoStock> findByTipo(String tipo);

    List<MovimientoStock> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}