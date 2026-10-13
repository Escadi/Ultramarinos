package com.example.backend.Repository.IMP;

import com.example.backend.Model.RolCliente;
import com.example.backend.Repository.Interface.RolClienteInterface;
import com.example.backend.Repository.JPA.RolClienteJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementacion de {@link RolClienteInterface} delegando en {@link RolClienteJPA}.
 */
@Repository
@RequiredArgsConstructor
public class RolClienteImpl implements RolClienteInterface {

    private final RolClienteJPA rolClienteJPA;

    @Override
    public List<RolCliente> findAll() {
        return rolClienteJPA.findAll();
    }

    @Override
    public Optional<RolCliente> findById(Long id) {
        return rolClienteJPA.findById(id);
    }

    @Override
    public RolCliente save(RolCliente rolCliente) {
        return rolClienteJPA.save(rolCliente);
    }

    @Override
    public void deleteById(Long id) {
        rolClienteJPA.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return rolClienteJPA.existsById(id);
    }

    @Override
    public long count() {
        return rolClienteJPA.count();
    }

    @Override
    public Optional<RolCliente> findByTipoCliente(String tipoCliente) {
        return rolClienteJPA.findByTipoCliente(tipoCliente);
    }
}