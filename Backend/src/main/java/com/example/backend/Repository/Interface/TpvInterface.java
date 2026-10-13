package com.example.backend.Repository.Interface;

import com.example.backend.Model.Tpv;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio de {@link Tpv}.
 */
public interface TpvInterface {

    List<Tpv> findAll();

    Optional<Tpv> findById(Long id);

    Tpv save(Tpv tpv);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();

    List<Tpv> findByActivoTrue();
}