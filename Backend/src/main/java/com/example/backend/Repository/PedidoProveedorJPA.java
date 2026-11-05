package com.example.backend.Repository;

import com.example.backend.Model.PedidoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoProveedorJPA extends JpaRepository<PedidoProveedor, Long> {
    Optional<PedidoProveedor> findByNumeroPedido(String numeroPedido);
}

