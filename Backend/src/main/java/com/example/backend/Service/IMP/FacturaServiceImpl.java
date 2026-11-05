package com.example.backend.Service.IMP;

import com.example.backend.Model.Factura;

import java.util.List;
import java.util.Optional;

public interface FacturaServiceImpl {
    List<Factura> getAll();
    Optional<Factura> getById(Long id);
    Factura save(Factura factura);
    void delete(Long id);
}

