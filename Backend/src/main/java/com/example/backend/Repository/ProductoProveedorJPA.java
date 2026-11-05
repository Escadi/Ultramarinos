package com.example.backend.Repository;

import com.example.backend.Model.ProductoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoProveedorJPA extends JpaRepository<ProductoProveedor, Long> {
    List<ProductoProveedor> findByProductoId(Long productoId);
    List<ProductoProveedor> findByProveedorId(Long proveedorId);
}

