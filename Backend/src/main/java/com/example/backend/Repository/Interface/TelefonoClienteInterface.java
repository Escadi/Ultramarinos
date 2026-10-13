package com.example.backend.Repository.Interface;

import com.example.backend.Model.TelefonoCliente;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio de {@link TelefonoCliente}.
 */
public interface TelefonoClienteInterface {

    List<TelefonoCliente> findAll();

    Optional<TelefonoCliente> findById(Long id);

    TelefonoCliente save(TelefonoCliente telefonoCliente);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();

    List<TelefonoCliente> findByClienteId(Long clienteId);
}