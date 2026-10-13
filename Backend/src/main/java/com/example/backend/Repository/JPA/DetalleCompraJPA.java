package com.example.backend.Repository.JPA;

import com.example.backend.Model.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio Spring Data JPA para {@link DetalleCompra}.
 */
public interface DetalleCompraJPA extends JpaRepository<DetalleCompra, Long> {

    List<DetalleCompra> findByCompraId(Long compraId);

    List<DetalleCompra> findByProductoId(Long productoId);
}