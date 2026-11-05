package com.example.backend.Service.IMP;

import com.example.backend.Model.ProductoProveedor;

import java.util.List;
import java.util.Optional;

public interface ProductoProveedorServiceImpl {
    List<ProductoProveedor> getAll();
    Optional<ProductoProveedor> getById(Long id);
    List<ProductoProveedor> getByProductoId(Long productoId);
    List<ProductoProveedor> getByProveedorId(Long proveedorId);
    ProductoProveedor save(ProductoProveedor productoProveedor);
    ProductoProveedor update(Long id, ProductoProveedor productoProveedor);
    void delete(Long id);
}

