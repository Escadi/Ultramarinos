package com.example.backend.Service.Service;

import com.example.backend.Model.TelefonoCliente;
import com.example.backend.Repository.TelefonoClienteJPA;
import com.example.backend.Service.IMP.TelefonoClienteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TelefonoClienteService implements TelefonoClienteServiceImpl {

    private final TelefonoClienteJPA telefonoClienteJPA;

    @Override
    public List<TelefonoCliente> getAll() {
        return telefonoClienteJPA.findAll();
    }

    @Override
    public Optional<TelefonoCliente> getById(Long id) {
        return telefonoClienteJPA.findById(id);
    }

    @Override
    public List<TelefonoCliente> getByClienteId(Long clienteId) {
        return telefonoClienteJPA.findByClienteId(clienteId);
    }

    @Override
    public TelefonoCliente save(TelefonoCliente telefonoCliente) {
        return telefonoClienteJPA.save(telefonoCliente);
    }

    @Override
    public TelefonoCliente update(Long id, TelefonoCliente telefonoCliente) {
        TelefonoCliente existing = telefonoClienteJPA.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TelefonoCliente no encontrado con ID: " + id));
        existing.setTelefonoCliente(telefonoCliente.getTelefonoCliente());
        existing.setCliente(telefonoCliente.getCliente());
        return telefonoClienteJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!telefonoClienteJPA.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TelefonoCliente no encontrado con ID: " + id);
        }
        telefonoClienteJPA.deleteById(id);
    }
}

