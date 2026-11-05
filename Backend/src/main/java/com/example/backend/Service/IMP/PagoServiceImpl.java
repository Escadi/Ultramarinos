package com.example.backend.Service.IMP;

import com.example.backend.Model.Pago;

import java.util.List;
import java.util.Optional;

public interface PagoServiceImpl {
    List<Pago> getAll();
    Optional<Pago> getById(Long id);
    List<Pago> getByVentaId(Long ventaId);
    Pago save(Pago pago);
    Pago update(Long id, Pago pago);
    void delete(Long id);
}

