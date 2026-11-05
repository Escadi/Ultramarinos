package com.example.backend.Repository;

import com.example.backend.Model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompraJPA extends JpaRepository<Compra, Long> {
    Optional<Compra> findByNumeroCompra(String numeroCompra);
}

