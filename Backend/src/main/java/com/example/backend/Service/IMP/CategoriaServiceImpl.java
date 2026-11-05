package com.example.backend.Service.IMP;

import com.example.backend.Model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaServiceImpl {
    List<Categoria> getAll();
    Optional<Categoria> getById(Long id);
    Categoria save(Categoria categoria);
    Categoria update(Long id, Categoria categoria);
    void delete(Long id);
}

