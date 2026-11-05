package com.example.backend.Service.IMP;

import com.example.backend.Model.TelefonoCliente;

import java.util.List;
import java.util.Optional;

public interface TelefonoClienteServiceImpl {
    List<TelefonoCliente> getAll();
    Optional<TelefonoCliente> getById(Long id);
    List<TelefonoCliente> getByClienteId(Long clienteId);
    TelefonoCliente save(TelefonoCliente telefonoCliente);
    TelefonoCliente update(Long id, TelefonoCliente telefonoCliente);
    void delete(Long id);
}

