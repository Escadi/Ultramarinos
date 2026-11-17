package com.example.backend.Service.Service;

import com.example.backend.Model.RolTrabajador;
import com.example.backend.Repository.RolTrabajadorJPA;
import com.example.backend.Service.IMP.RolTrabajadorServiceImpl;
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
public class RolTrabajadorService implements RolTrabajadorServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(RolTrabajadorService.class);

    private final RolTrabajadorJPA rolTrabajadorJPA;

    @Override
    public List<RolTrabajador> getAll() {
        logger.info("Obteniendo listado de todos los roles de trabajador");
        return rolTrabajadorJPA.findAll();
    }

    @Override
    public Optional<RolTrabajador> getById(Long id) {
        logger.info("Buscando rol de trabajador por ID: {}", id);
        return rolTrabajadorJPA.findById(id);
    }

    @Override
    public RolTrabajador save(RolTrabajador rolTrabajador) {
        logger.info("Guardando nuevo rol de trabajador: {}", rolTrabajador.getNombreRol());
        return rolTrabajadorJPA.save(rolTrabajador);
    }

    @Override
    public RolTrabajador update(Long id, RolTrabajador rolTrabajador) {
        logger.info("Actualizando rol de trabajador con ID: {}", id);
        RolTrabajador existing = rolTrabajadorJPA.findById(id)
                .orElseThrow(() -> {
                    logger.warn("RolTrabajador no encontrado con ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "RolTrabajador no encontrado con ID: " + id);
                });
        existing.setNombreRol(rolTrabajador.getNombreRol());
        return rolTrabajadorJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        logger.info("Eliminando rol de trabajador con ID: {}", id);
        if (!rolTrabajadorJPA.existsById(id)) {
            logger.warn("No se puede eliminar, RolTrabajador no encontrado con ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "RolTrabajador no encontrado con ID: " + id);
        }
        rolTrabajadorJPA.deleteById(id);
    }
}
