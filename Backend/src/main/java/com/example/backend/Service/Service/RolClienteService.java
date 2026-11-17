package com.example.backend.Service.Service;

import com.example.backend.Model.RolCliente;
import com.example.backend.Repository.RolClienteJPA;
import com.example.backend.Service.IMP.RolClienteServiceImpl;
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
public class RolClienteService implements RolClienteServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(RolClienteService.class);

    private final RolClienteJPA rolClienteJPA;

    @Override
    public List<RolCliente> getAll() {
        logger.info("Obteniendo listado de todos los roles de cliente");
        return rolClienteJPA.findAll();
    }

    @Override
    public Optional<RolCliente> getById(Long id) {
        logger.info("Buscando rol de cliente por ID: {}", id);
        return rolClienteJPA.findById(id);
    }

    @Override
    public RolCliente save(RolCliente rolCliente) {
        logger.info("Guardando nuevo rol de cliente: {}", rolCliente.getTipoCliente());
        return rolClienteJPA.save(rolCliente);
    }

    @Override
    public RolCliente update(Long id, RolCliente rolCliente) {
        logger.info("Actualizando rol de cliente con ID: {}", id);
        RolCliente existing = rolClienteJPA.findById(id)
                .orElseThrow(() -> {
                    logger.warn("RolCliente no encontrado con ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "RolCliente no encontrado con ID: " + id);
                });
        existing.setTipoCliente(rolCliente.getTipoCliente());
        return rolClienteJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        logger.info("Eliminando rol de cliente con ID: {}", id);
        if (!rolClienteJPA.existsById(id)) {
            logger.warn("No se puede eliminar, RolCliente no encontrado con ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "RolCliente no encontrado con ID: " + id);
        }
        rolClienteJPA.deleteById(id);
    }
}
