package com.example.backend.Service.IMP;

import com.example.backend.Model.Tpv;

import java.util.List;
import java.util.Optional;

public interface TpvServiceImpl {
    List<Tpv> getAll();
    Optional<Tpv> getById(Long id);
    Tpv save(Tpv tpv);
    Tpv update(Long id, Tpv tpv);
    void delete(Long id);
}

