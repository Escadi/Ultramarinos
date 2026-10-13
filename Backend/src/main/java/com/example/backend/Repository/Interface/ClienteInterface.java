package com.example.backend.Repository.Interface;

import com.example.backend.Model.Cliente;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio de {@link Cliente}.
 */
public interface ClienteInterface {

    List<Cliente> findAll();

    Optional<Cliente> findById(Long id);

    Cliente save(Cliente cliente);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();

    Optional<Cliente> findByCorreoElectronico(String correoElectronico);

    List<Cliente> findByNombreContainingIgnoreCase(String nombre);

    List<Cliente> findByRolClienteId(Long rolClienteId);
}