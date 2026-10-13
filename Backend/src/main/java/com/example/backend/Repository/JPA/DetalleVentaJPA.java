package com.example.backend.Repository.JPA;

import com.example.backend.Model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio Spring Data JPA para {@link DetalleVenta}.
 */
public interface DetalleVentaJPA extends JpaRepository<DetalleVenta, Long> {

    List<DetalleVenta> findByVentaId(Long ventaId);

    List<DetalleVenta> findByProductoId(Long productoId);
}