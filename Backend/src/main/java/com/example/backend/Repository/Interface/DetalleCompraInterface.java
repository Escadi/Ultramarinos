package com.example.backend.Repository.Interface;

import com.example.backend.Model.DetalleCompra;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio de {@link DetalleCompra}.
 */
public interface DetalleCompraInterface {

    List<DetalleCompra> findAll();

    Optional<DetalleCompra> findById(Long id);

    DetalleCompra save(DetalleCompra detalleCompra);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();

    List<DetalleCompra> findByCompraId(Long compraId);

    List<DetalleCompra> findByProductoId(Long productoId);
}