package com.example.backend.Repository.IMP;

import com.example.backend.Model.Cliente;
import com.example.backend.Repository.Interface.ClienteInterface;
import com.example.backend.Repository.JPA.ClienteJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementacion de {@link ClienteInterface} delegando en {@link ClienteJPA}.
 */
@Repository
@RequiredArgsConstructor
public class ClienteImpl implements ClienteInterface {

    private final ClienteJPA clienteJPA;

    @Override
    public List<Cliente> findAll() {
        return clienteJPA.findAll();
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return clienteJPA.findById(id);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteJPA.save(cliente);
    }

    @Override
    public void deleteById(Long id) {
        clienteJPA.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return clienteJPA.existsById(id);
    }

    @Override
    public long count() {
        return clienteJPA.count();
    }

    @Override
    public Optional<Cliente> findByCorreoElectronico(String correoElectronico) {
        return clienteJPA.findByCorreoElectronico(correoElectronico);
    }

    @Override
    public List<Cliente> findByNombreContainingIgnoreCase(String nombre) {
        return clienteJPA.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Cliente> findByRolClienteId(Long rolClienteId) {
        return clienteJPA.findByRolClienteId(rolClienteId);
    }
}