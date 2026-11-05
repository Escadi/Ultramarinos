package com.example.backend.Service.IMP;

import com.example.backend.Model.RolCliente;

import java.util.List;
import java.util.Optional;

public interface RolClienteServiceImpl {
    List<RolCliente> getAll();
    Optional<RolCliente> getById(Long id);
    RolCliente save(RolCliente rolCliente);
    RolCliente update(Long id, RolCliente rolCliente);
    void delete(Long id);
}

