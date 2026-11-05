package com.example.backend.Service.Service;

import com.example.backend.Model.RolTrabajador;
import com.example.backend.Repository.RolTrabajadorJPA;
import com.example.backend.Service.IMP.RolTrabajadorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolTrabajadorService implements RolTrabajadorServiceImpl {

    private final RolTrabajadorJPA rolTrabajadorJPA;

    @Override
    public List<RolTrabajador> getAll() {
        return rolTrabajadorJPA.findAll();
    }

    @Override
    public Optional<RolTrabajador> getById(Long id) {
        return rolTrabajadorJPA.findById(id);
    }

    @Override
    public RolTrabajador save(RolTrabajador rolTrabajador) {
        return rolTrabajadorJPA.save(rolTrabajador);
    }

    @Override
    public RolTrabajador update(Long id, RolTrabajador rolTrabajador) {
        RolTrabajador existing = rolTrabajadorJPA.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RolTrabajador no encontrado con ID: " + id));
        existing.setNombreRol(rolTrabajador.getNombreRol());
        return rolTrabajadorJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!rolTrabajadorJPA.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "RolTrabajador no encontrado con ID: " + id);
        }
        rolTrabajadorJPA.deleteById(id);
    }
}

