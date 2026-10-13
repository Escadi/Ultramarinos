package com.example.backend.Repository.IMP;

import com.example.backend.Model.Tpv;
import com.example.backend.Repository.Interface.TpvInterface;
import com.example.backend.Repository.JPA.TpvJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementacion de {@link TpvInterface} delegando en {@link TpvJPA}.
 */
@Repository
@RequiredArgsConstructor
public class TpvImpl implements TpvInterface {

    private final TpvJPA tpvJPA;

    @Override
    public List<Tpv> findAll() {
        return tpvJPA.findAll();
    }

    @Override
    public Optional<Tpv> findById(Long id) {
        return tpvJPA.findById(id);
    }

    @Override
    public Tpv save(Tpv tpv) {
        return tpvJPA.save(tpv);
    }

    @Override
    public void deleteById(Long id) {
        tpvJPA.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return tpvJPA.existsById(id);
    }

    @Override
    public long count() {
        return tpvJPA.count();
    }

    @Override
    public List<Tpv> findByActivoTrue() {
        return tpvJPA.findByActivoTrue();
    }
}