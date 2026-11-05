package com.example.backend.Service.Service;

import com.example.backend.Model.TurnoCaja;
import com.example.backend.Repository.TurnoCajaJPA;
import com.example.backend.Service.IMP.TurnoCajaServiceImpl;
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
public class TurnoCajaService implements TurnoCajaServiceImpl {

    private final TurnoCajaJPA turnoCajaJPA;

    @Override
    public List<TurnoCaja> getAll() {
        return turnoCajaJPA.findAll();
    }

    @Override
    public Optional<TurnoCaja> getById(Long id) {
        return turnoCajaJPA.findById(id);
    }

    @Override
    public TurnoCaja save(TurnoCaja turnoCaja) {
        return turnoCajaJPA.save(turnoCaja);
    }

    @Override
    public TurnoCaja update(Long id, TurnoCaja turnoCaja) {
        TurnoCaja existing = turnoCajaJPA.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TurnoCaja no encontrado con ID: " + id));
        existing.setFondoCaja(turnoCaja.getFondoCaja());
        existing.setEstadoCaja(turnoCaja.getEstadoCaja());
        existing.setSaldoReal(turnoCaja.getSaldoReal());
        existing.setSaldoEsperado(turnoCaja.getSaldoEsperado());
        existing.setEmpleado(turnoCaja.getEmpleado());
        return turnoCajaJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!turnoCajaJPA.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TurnoCaja no encontrado con ID: " + id);
        }
        turnoCajaJPA.deleteById(id);
    }
}

