package com.example.backend.Repository.Interface;

import com.example.backend.Model.RolCliente;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio de {@link RolCliente}.
 */
public interface RolClienteInterface {

    List<RolCliente> findAll();

    Optional<RolCliente> findById(Long id);

    RolCliente save(RolCliente rolCliente);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();

    Optional<RolCliente> findByTipoCliente(String tipoCliente);
}