package com.example.backend.Service.IMP;

import com.example.backend.Model.Proveedor;

import java.util.List;
import java.util.Optional;

public interface ProveedorServiceImpl {
    List<Proveedor> getAll();
    Optional<Proveedor> getById(Long id);
    Proveedor save(Proveedor proveedor);
    Proveedor update(Long id, Proveedor proveedor);
    void delete(Long id);
}

