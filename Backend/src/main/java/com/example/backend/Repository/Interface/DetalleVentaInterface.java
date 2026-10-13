package com.example.backend.Repository.Interface;

import com.example.backend.Model.DetalleVenta;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio de {@link DetalleVenta}.
 */
public interface DetalleVentaInterface {

    List<DetalleVenta> findAll();

    Optional<DetalleVenta> findById(Long id);

    DetalleVenta save(DetalleVenta detalleVenta);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();

    List<DetalleVenta> findByVentaId(Long ventaId);

    List<DetalleVenta> findByProductoId(Long productoId);
}