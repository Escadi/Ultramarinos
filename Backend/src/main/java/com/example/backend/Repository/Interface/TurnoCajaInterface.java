package com.example.backend.Repository.Interface;

import com.example.backend.Model.TurnoCaja;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio de {@link TurnoCaja}.
 */
public interface TurnoCajaInterface {

    List<TurnoCaja> findAll();

    Optional<TurnoCaja> findById(Long id);

    TurnoCaja save(TurnoCaja turnoCaja);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();

    List<TurnoCaja> findByEmpleadoId(Long empleadoId);

    List<TurnoCaja> findByEstadoCaja(String estadoCaja);
}