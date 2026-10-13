package com.example.backend.Repository.JPA;

import com.example.backend.Model.ProductoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio Spring Data JPA para {@link ProductoProveedor}.
 */
public interface ProductoProveedorJPA extends JpaRepository<ProductoProveedor, Long> {

    List<ProductoProveedor> findByProductoId(Long productoId);

    List<ProductoProveedor> findByProveedorId(Long proveedorId);

    Optional<ProductoProveedor> findByProductoIdAndProveedorId(Long productoId, Long proveedorId);

    List<ProductoProveedor> findByProductoIdAndPreferenteTrue(Long productoId);
}