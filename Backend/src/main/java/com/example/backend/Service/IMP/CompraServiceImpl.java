package com.example.backend.Service.IMP;

import com.example.backend.Model.Compra;

import java.util.List;
import java.util.Optional;

public interface CompraServiceImpl {
    List<Compra> getAll();
    Optional<Compra> getById(Long id);
    Compra save(Compra compra);
    Compra update(Long id, Compra compra);
    void delete(Long id);
}

