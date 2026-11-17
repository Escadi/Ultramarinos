package com.example.backend.Service.Service;

import com.example.backend.Model.Tpv;
import com.example.backend.Repository.TpvJPA;
import com.example.backend.Service.IMP.TpvServiceImpl;
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
public class TpvService implements TpvServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(TpvService.class);

    private final TpvJPA tpvJPA;

    @Override
    public List<Tpv> getAll() {
        logger.info("Obteniendo listado de todos los TPVs");
        return tpvJPA.findAll();
    }

    @Override
    public Optional<Tpv> getById(Long id) {
        logger.info("Buscando TPV por ID: {}", id);
        return tpvJPA.findById(id);
    }

    @Override
    public Tpv save(Tpv tpv) {
        logger.info("Guardando nuevo TPV: {}", tpv.getNombreTerminal());
        return tpvJPA.save(tpv);
    }

    @Override
    public Tpv update(Long id, Tpv tpv) {
        logger.info("Actualizando TPV con ID: {}", id);
        Tpv existing = tpvJPA.findById(id)
                .orElseThrow(() -> {
                    logger.warn("TPV no encontrado con ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "TPV no encontrado con ID: " + id);
                });
        existing.setNombreTerminal(tpv.getNombreTerminal());
        existing.setActivo(tpv.getActivo());
        existing.setUbicacion(tpv.getUbicacion());
        return tpvJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        logger.info("Eliminando TPV con ID: {}", id);
        if (!tpvJPA.existsById(id)) {
            logger.warn("No se puede eliminar, TPV no encontrado con ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TPV no encontrado con ID: " + id);
        }
        tpvJPA.deleteById(id);
    }
}
