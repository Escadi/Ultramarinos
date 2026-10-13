package com.example.backend.Repository.IMP;

import com.example.backend.Model.ProductoProveedor;
import com.example.backend.Repository.Interface.ProductoProveedorInterface;
import com.example.backend.Repository.JPA.ProductoProveedorJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementacion de {@link ProductoProveedorInterface} delegando en {@link ProductoProveedorJPA}.
 */
@Repository
@RequiredArgsConstructor
public class ProductoProveedorImpl implements ProductoProveedorInterface {

    private final ProductoProveedorJPA productoProveedorJPA;

    @Override
    public List<ProductoProveedor> findAll() {
        return productoProveedorJPA.findAll();
    }

    @Override
    public Optional<ProductoProveedor> findById(Long id) {
        return productoProveedorJPA.findById(id);
    }

    @Override
    public ProductoProveedor save(ProductoProveedor productoProveedor) {
        return productoProveedorJPA.save(productoProveedor);
    }

    @Override
    public void deleteById(Long id) {
        productoProveedorJPA.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return productoProveedorJPA.existsById(id);
    }

    @Override
    public long count() {
        return productoProveedorJPA.count();
    }

    @Override
    public List<ProductoProveedor> findByProductoId(Long productoId) {
        return productoProveedorJPA.findByProductoId(productoId);
    }

    @Override
    public List<ProductoProveedor> findByProveedorId(Long proveedorId) {
        return productoProveedorJPA.findByProveedorId(proveedorId);
    }

    @Override
    public Optional<ProductoProveedor> findByProductoIdAndProveedorId(Long productoId, Long proveedorId) {
        return productoProveedorJPA.findByProductoIdAndProveedorId(productoId, proveedorId);
    }

    @Override
    public List<ProductoProveedor> findByProductoIdAndPreferenteTrue(Long productoId) {
        return productoProveedorJPA.findByProductoIdAndPreferenteTrue(productoId);
    }
}