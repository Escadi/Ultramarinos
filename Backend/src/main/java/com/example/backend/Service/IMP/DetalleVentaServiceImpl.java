package com.example.backend.Service.IMP;

import com.example.backend.Model.DetalleVenta;

import java.util.List;
import java.util.Optional;

public interface DetalleVentaServiceImpl {
    List<DetalleVenta> getAll();
    Optional<DetalleVenta> getById(Long id);
    List<DetalleVenta> getByVentaId(Long ventaId);
    DetalleVenta save(DetalleVenta detalleVenta);
    DetalleVenta update(Long id, DetalleVenta detalleVenta);
    void delete(Long id);
}

