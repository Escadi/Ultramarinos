package com.example.backend.Service.Service;

import com.example.backend.Model.TelefonoCliente;
import com.example.backend.Repository.TelefonoClienteJPA;
import com.example.backend.Service.IMP.TelefonoClienteServiceImpl;
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
public class TelefonoClienteService implements TelefonoClienteServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(TelefonoClienteService.class);

    private final TelefonoClienteJPA telefonoClienteJPA;

    @Override
    public List<TelefonoCliente> getAll() {
        logger.info("Obteniendo listado de todos los telefonos de clientes");
        return telefonoClienteJPA.findAll();
    }

    @Override
    public Optional<TelefonoCliente> getById(Long id) {
        logger.info("Buscando telefono de cliente por ID: {}", id);
        return telefonoClienteJPA.findById(id);
    }

    @Override
    public List<TelefonoCliente> getByClienteId(Long clienteId) {
        logger.info("Obteniendo telefonos para el cliente ID: {}", clienteId);
        return telefonoClienteJPA.findByClienteId(clienteId);
    }

    @Override
    public TelefonoCliente save(TelefonoCliente telefonoCliente) {
        logger.info("Guardando telefono de cliente: {}", telefonoCliente.getTelefonoCliente());
        return telefonoClienteJPA.save(telefonoCliente);
    }

    @Override
    public TelefonoCliente update(Long id, TelefonoCliente telefonoCliente) {
        logger.info("Actualizando telefono de cliente con ID: {}", id);
        TelefonoCliente existing = telefonoClienteJPA.findById(id)
                .orElseThrow(() -> {
                    logger.warn("TelefonoCliente no encontrado con ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "TelefonoCliente no encontrado con ID: " + id);
                });
        existing.setTelefonoCliente(telefonoCliente.getTelefonoCliente());
        existing.setCliente(telefonoCliente.getCliente());
        return telefonoClienteJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        logger.info("Eliminando telefono de cliente con ID: {}", id);
        if (!telefonoClienteJPA.existsById(id)) {
            logger.warn("No se puede eliminar, TelefonoCliente no encontrado con ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TelefonoCliente no encontrado con ID: " + id);
        }
        telefonoClienteJPA.deleteById(id);
    }
}
