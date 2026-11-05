package com.example.backend.Service.Service;

import com.example.backend.Model.RolCliente;
import com.example.backend.Repository.RolClienteJPA;
import com.example.backend.Service.IMP.RolClienteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolClienteService implements RolClienteServiceImpl {

    private final RolClienteJPA rolClienteJPA;

    @Override
    public List<RolCliente> getAll() {
        return rolClienteJPA.findAll();
    }

    @Override
    public Optional<RolCliente> getById(Long id) {
        return rolClienteJPA.findById(id);
    }

    @Override
    public RolCliente save(RolCliente rolCliente) {
        return rolClienteJPA.save(rolCliente);
    }

    @Override
    public RolCliente update(Long id, RolCliente rolCliente) {
        RolCliente existing = rolClienteJPA.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RolCliente no encontrado con ID: " + id));
        existing.setTipoCliente(rolCliente.getTipoCliente());
        return rolClienteJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!rolClienteJPA.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "RolCliente no encontrado con ID: " + id);
        }
        rolClienteJPA.deleteById(id);
    }
}

