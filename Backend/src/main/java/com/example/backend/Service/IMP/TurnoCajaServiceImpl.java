package com.example.backend.Service.IMP;

import com.example.backend.Model.TurnoCaja;

import java.util.List;
import java.util.Optional;

public interface TurnoCajaServiceImpl {
    List<TurnoCaja> getAll();
    Optional<TurnoCaja> getById(Long id);
    TurnoCaja save(TurnoCaja turnoCaja);
    TurnoCaja update(Long id, TurnoCaja turnoCaja);
    void delete(Long id);
}

