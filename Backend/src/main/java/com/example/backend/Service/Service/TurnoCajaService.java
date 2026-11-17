package com.example.backend.Service.Service;

import com.example.backend.Model.TurnoCaja;
import com.example.backend.Repository.TurnoCajaJPA;
import com.example.backend.Service.IMP.TurnoCajaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TurnoCajaService implements TurnoCajaServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(TurnoCajaService.class);

    private final TurnoCajaJPA turnoCajaJPA;

    @Override
    public List<TurnoCaja> getAll() {
        logger.info("Obteniendo listado de todos los turnos de caja");
        return turnoCajaJPA.findAll();
    }

    @Override
    public Optional<TurnoCaja> getById(Long id) {
        logger.info("Buscando turno de caja por ID: {}", id);
        return turnoCajaJPA.findById(id);
    }

    @Override
    public TurnoCaja save(TurnoCaja turnoCaja) {
        logger.info("Guardando nuevo turno de caja");
        return turnoCajaJPA.save(turnoCaja);
    }

    @Override
    public TurnoCaja update(Long id, TurnoCaja turnoCaja) {
        logger.info("Actualizando turno de caja con ID: {}", id);
        TurnoCaja existing = turnoCajaJPA.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Turno de caja no encontrado con ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "TurnoCaja no encontrado con ID: " + id);
                });
        existing.setFondoCaja(turnoCaja.getFondoCaja());
        existing.setEstadoCaja(turnoCaja.getEstadoCaja());
        existing.setSaldoReal(turnoCaja.getSaldoReal());
        existing.setSaldoEsperado(turnoCaja.getSaldoEsperado());
        existing.setEmpleado(turnoCaja.getEmpleado());
        return turnoCajaJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        logger.info("Eliminando turno de caja con ID: {}", id);
        if (!turnoCajaJPA.existsById(id)) {
            logger.warn("No se puede eliminar, turno de caja no encontrado con ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TurnoCaja no encontrado con ID: " + id);
        }
        turnoCajaJPA.deleteById(id);
    }
}
