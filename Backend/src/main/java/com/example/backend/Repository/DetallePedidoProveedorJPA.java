package com.example.backend.Repository;

import com.example.backend.Model.DetallePedidoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePedidoProveedorJPA extends JpaRepository<DetallePedidoProveedor, Long> {
    List<DetallePedidoProveedor> findByPedidoProveedorId(Long pedidoProveedorId);
}

