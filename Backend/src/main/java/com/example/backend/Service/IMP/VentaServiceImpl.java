package com.example.backend.Service.IMP;

import com.example.backend.Model.Venta;

import java.util.List;
import java.util.Optional;

public interface VentaServiceImpl {
    List<Venta> getAll();
    Optional<Venta> getById(Long id);
    Venta save(Venta venta);
    Venta update(Long id, Venta venta);
    void delete(Long id);
}

