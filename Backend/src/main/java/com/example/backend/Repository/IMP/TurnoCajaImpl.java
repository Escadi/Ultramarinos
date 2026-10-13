package com.example.backend.Repository.IMP;

import com.example.backend.Model.TurnoCaja;
import com.example.backend.Repository.Interface.TurnoCajaInterface;
import com.example.backend.Repository.JPA.TurnoCajaJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementacion de {@link TurnoCajaInterface} delegando en {@link TurnoCajaJPA}.
 */
@Repository
@RequiredArgsConstructor
public class TurnoCajaImpl implements TurnoCajaInterface {

    private final TurnoCajaJPA turnoCajaJPA;

    @Override
    public List<TurnoCaja> findAll() {
        return turnoCajaJPA.findAll();
    }

    @Override
    public Optional<TurnoCaja> findById(Long id) {
        return turnoCajaJPA.findById(id);
    }

    @Override
    public TurnoCaja save(TurnoCaja turnoCaja) {
        return turnoCajaJPA.save(turnoCaja);
    }

    @Override
    public void deleteById(Long id) {
        turnoCajaJPA.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return turnoCajaJPA.existsById(id);
    }

    @Override
    public long count() {
        return turnoCajaJPA.count();
    }

    @Override
    public List<TurnoCaja> findByEmpleadoId(Long empleadoId) {
        return turnoCajaJPA.findByEmpleadoId(empleadoId);
    }

    @Override
    public List<TurnoCaja> findByEstadoCaja(String estadoCaja) {
        return turnoCajaJPA.findByEstadoCaja(estadoCaja);
    }
}