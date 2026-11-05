package com.example.backend.Service.IMP;

import com.example.backend.Model.MovimientoStock;

import java.util.List;
import java.util.Optional;

public interface MovimientoStockServiceImpl {
    List<MovimientoStock> getAll();
    Optional<MovimientoStock> getById(Long id);
    List<MovimientoStock> getByProductoId(Long productoId);
    MovimientoStock save(MovimientoStock movimientoStock);
    void delete(Long id);
}

