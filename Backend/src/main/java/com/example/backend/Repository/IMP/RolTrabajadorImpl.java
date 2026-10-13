package com.example.backend.Repository.IMP;

import com.example.backend.Model.RolTrabajador;
import com.example.backend.Repository.Interface.RolTrabajadorInterface;
import com.example.backend.Repository.JPA.RolTrabajadorJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementacion de {@link RolTrabajadorInterface} delegando en {@link RolTrabajadorJPA}.
 */
@Repository
@RequiredArgsConstructor
public class RolTrabajadorImpl implements RolTrabajadorInterface {

    private final RolTrabajadorJPA rolTrabajadorJPA;

    @Override
    public List<RolTrabajador> findAll() {
        return rolTrabajadorJPA.findAll();
    }

    @Override
    public Optional<RolTrabajador> findById(Long id) {
        return rolTrabajadorJPA.findById(id);
    }

    @Override
    public RolTrabajador save(RolTrabajador rolTrabajador) {
        return rolTrabajadorJPA.save(rolTrabajador);
    }

    @Override
    public void deleteById(Long id) {
        rolTrabajadorJPA.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return rolTrabajadorJPA.existsById(id);
    }

    @Override
    public long count() {
        return rolTrabajadorJPA.count();
    }

    @Override
    public Optional<RolTrabajador> findByNombreRol(String nombreRol) {
        return rolTrabajadorJPA.findByNombreRol(nombreRol);
    }
}