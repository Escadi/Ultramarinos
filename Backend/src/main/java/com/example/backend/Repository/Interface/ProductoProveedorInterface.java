package com.example.backend.Repository.Interface;

import com.example.backend.Model.ProductoProveedor;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio de {@link ProductoProveedor}.
 */
public interface ProductoProveedorInterface {

    List<ProductoProveedor> findAll();

    Optional<ProductoProveedor> findById(Long id);

    ProductoProveedor save(ProductoProveedor productoProveedor);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();

    List<ProductoProveedor> findByProductoId(Long productoId);

    List<ProductoProveedor> findByProveedorId(Long proveedorId);

    Optional<ProductoProveedor> findByProductoIdAndProveedorId(Long productoId, Long proveedorId);

    List<ProductoProveedor> findByProductoIdAndPreferenteTrue(Long productoId);
}