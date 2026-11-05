package com.example.backend.Service.IMP;

import com.example.backend.Model.PedidoProveedor;

import java.util.List;
import java.util.Optional;

public interface PedidoProveedorServiceImpl {
    List<PedidoProveedor> getAll();
    Optional<PedidoProveedor> getById(Long id);
    PedidoProveedor save(PedidoProveedor pedidoProveedor);
    PedidoProveedor update(Long id, PedidoProveedor pedidoProveedor);
    void delete(Long id);
}

