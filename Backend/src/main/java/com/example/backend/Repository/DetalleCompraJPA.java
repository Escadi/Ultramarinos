package com.example.backend.Repository;

import com.example.backend.Model.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleCompraJPA extends JpaRepository<DetalleCompra, Long> {
    List<DetalleCompra> findByCompraId(Long compraId);
}

