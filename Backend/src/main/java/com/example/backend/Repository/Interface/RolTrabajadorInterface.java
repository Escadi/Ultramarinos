package com.example.backend.Repository.Interface;

import com.example.backend.Model.RolTrabajador;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio de {@link RolTrabajador}.
 */
public interface RolTrabajadorInterface {

    List<RolTrabajador> findAll();

    Optional<RolTrabajador> findById(Long id);

    RolTrabajador save(RolTrabajador rolTrabajador);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();

    Optional<RolTrabajador> findByNombreRol(String nombreRol);
}