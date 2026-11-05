package com.example.backend.Service.IMP;

import com.example.backend.Model.DetalleCompra;

import java.util.List;
import java.util.Optional;

public interface DetalleCompraServiceImpl {
    List<DetalleCompra> getAll();
    Optional<DetalleCompra> getById(Long id);
    List<DetalleCompra> getByCompraId(Long compraId);
    DetalleCompra save(DetalleCompra detalleCompra);
    DetalleCompra update(Long id, DetalleCompra detalleCompra);
    void delete(Long id);
}

