package com.example.backend.Repository.JPA;

import com.example.backend.Model.MovimientoStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio Spring Data JPA para {@link MovimientoStock}.
 */
public interface MovimientoStockJPA extends JpaRepository<MovimientoStock, Long> {

    List<MovimientoStock> findByProductoId(Long productoId);

    List<MovimientoStock> findByEmpleadoId(Long empleadoId);

    List<MovimientoStock> findByTipo(String tipo);

    List<MovimientoStock> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}