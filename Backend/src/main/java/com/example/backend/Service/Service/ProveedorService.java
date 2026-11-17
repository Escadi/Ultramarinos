package com.example.backend.Service.Service;

import com.example.backend.Model.Proveedor;
import com.example.backend.Repository.ProveedorJPA;
import com.example.backend.Service.IMP.ProveedorServiceImpl;
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
public class ProveedorService implements ProveedorServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(ProveedorService.class);

    private final ProveedorJPA proveedorJPA;

    @Override
    public List<Proveedor> getAll() {
        logger.info("Obteniendo listado de todos los proveedores");
        return proveedorJPA.findAll();
    }

    @Override
    public Optional<Proveedor> getById(Long id) {
        logger.info("Buscando proveedor por ID: {}", id);
        return proveedorJPA.findById(id);
    }

    @Override
    public Proveedor save(Proveedor proveedor) {
        logger.info("Guardando nuevo proveedor: {}", proveedor.getNombreProveedor());
        return proveedorJPA.save(proveedor);
    }

    @Override
    public Proveedor update(Long id, Proveedor proveedor) {
        logger.info("Actualizando proveedor con ID: {}", id);
        Proveedor existing = proveedorJPA.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Proveedor no encontrado con ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado con ID: " + id);
                });
        existing.setCif(proveedor.getCif());
        existing.setContacto(proveedor.getContacto());
        existing.setNombreProveedor(proveedor.getNombreProveedor());
        existing.setActivo(proveedor.getActivo());
        existing.setEmail(proveedor.getEmail());
        existing.setTelefono(proveedor.getTelefono());
        return proveedorJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        logger.info("Eliminando proveedor con ID: {}", id);
        if (!proveedorJPA.existsById(id)) {
            logger.warn("No se puede eliminar, proveedor no encontrado con ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado con ID: " + id);
        }
        proveedorJPA.deleteById(id);
    }
}
