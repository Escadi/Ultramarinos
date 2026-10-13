package com.example.backend.Repository.IMP;

import com.example.backend.Model.TelefonoCliente;
import com.example.backend.Repository.Interface.TelefonoClienteInterface;
import com.example.backend.Repository.JPA.TelefonoClienteJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementacion de {@link TelefonoClienteInterface} delegando en {@link TelefonoClienteJPA}.
 */
@Repository
@RequiredArgsConstructor
public class TelefonoClienteImpl implements TelefonoClienteInterface {

    private final TelefonoClienteJPA telefonoClienteJPA;

    @Override
    public List<TelefonoCliente> findAll() {
        return telefonoClienteJPA.findAll();
    }

    @Override
    public Optional<TelefonoCliente> findById(Long id) {
        return telefonoClienteJPA.findById(id);
    }

    @Override
    public TelefonoCliente save(TelefonoCliente telefonoCliente) {
        return telefonoClienteJPA.save(telefonoCliente);
    }

    @Override
    public void deleteById(Long id) {
        telefonoClienteJPA.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return telefonoClienteJPA.existsById(id);
    }

    @Override
    public long count() {
        return telefonoClienteJPA.count();
    }

    @Override
    public List<TelefonoCliente> findByClienteId(Long clienteId) {
        return telefonoClienteJPA.findByClienteId(clienteId);
    }
}