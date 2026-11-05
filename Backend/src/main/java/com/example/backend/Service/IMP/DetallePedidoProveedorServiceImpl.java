package com.example.backend.Service.IMP;

import com.example.backend.Model.DetallePedidoProveedor;

import java.util.List;
import java.util.Optional;

public interface DetallePedidoProveedorServiceImpl {
    List<DetallePedidoProveedor> getAll();
    Optional<DetallePedidoProveedor> getById(Long id);
    List<DetallePedidoProveedor> getByPedidoProveedorId(Long pedidoId);
    DetallePedidoProveedor save(DetallePedidoProveedor detalle);
    DetallePedidoProveedor update(Long id, DetallePedidoProveedor detalle);
    void delete(Long id);
}

