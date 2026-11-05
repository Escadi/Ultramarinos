package com.example.backend.Service.Service;

import com.example.backend.Model.Proveedor;
import com.example.backend.Repository.ProveedorJPA;
import com.example.backend.Service.IMP.ProveedorServiceImpl;
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
public class ProveedorService implements ProveedorServiceImpl {

    private final ProveedorJPA proveedorJPA;

    @Override
    public List<Proveedor> getAll() {
        return proveedorJPA.findAll();
    }

    @Override
    public Optional<Proveedor> getById(Long id) {
        return proveedorJPA.findById(id);
    }

    @Override
    public Proveedor save(Proveedor proveedor) {
        return proveedorJPA.save(proveedor);
    }

    @Override
    public Proveedor update(Long id, Proveedor proveedor) {
        Proveedor existing = proveedorJPA.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado con ID: " + id));
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
        if (!proveedorJPA.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado con ID: " + id);
        }
        proveedorJPA.deleteById(id);
    }
}

