package com.example.backend.Service.Service;

import com.example.backend.Model.Tpv;
import com.example.backend.Repository.TpvJPA;
import com.example.backend.Service.IMP.TpvServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class TpvService implements TpvServiceImpl {

    private final TpvJPA tpvJPA;

    @Override
    public List<Tpv> getAll() {
        return tpvJPA.findAll();
    }

    @Override
    public Optional<Tpv> getById(Long id) {
        return tpvJPA.findById(id);
    }

    @Override
    public Tpv save(Tpv tpv) {
        return tpvJPA.save(tpv);
    }

    @Override
    public Tpv update(Long id, Tpv tpv) {
        Tpv existing = tpvJPA.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TPV no encontrado con ID: " + id));
        existing.setNombreTerminal(tpv.getNombreTerminal());
        existing.setActivo(tpv.getActivo());
        existing.setUbicacion(tpv.getUbicacion());
        return tpvJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!tpvJPA.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TPV no encontrado con ID: " + id);
        }
        tpvJPA.deleteById(id);
    }
}

