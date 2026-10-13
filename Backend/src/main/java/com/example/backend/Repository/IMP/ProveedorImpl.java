package com.example.backend.Repository.IMP;

import com.example.backend.Model.Proveedor;
import com.example.backend.Repository.Interface.ProveedorInterface;
import com.example.backend.Repository.JPA.ProveedorJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementacion de {@link ProveedorInterface} delegando en {@link ProveedorJPA}.
 */
@Repository
@RequiredArgsConstructor
public class ProveedorImpl implements ProveedorInterface {

    private final ProveedorJPA proveedorJPA;

    @Override
    public List<Proveedor> findAll() {
        return proveedorJPA.findAll();
    }

    @Override
    public Optional<Proveedor> findById(Long id) {
        return proveedorJPA.findById(id);
    }

    @Override
    public Proveedor save(Proveedor proveedor) {
        return proveedorJPA.save(proveedor);
    }

    @Override
    public void deleteById(Long id) {
        proveedorJPA.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return proveedorJPA.existsById(id);
    }

    @Override
    public long count() {
        return proveedorJPA.count();
    }

    @Override
    public Optional<Proveedor> findByCif(String cif) {
        return proveedorJPA.findByCif(cif);
    }

    @Override
    public List<Proveedor> findByNombreProveedorContainingIgnoreCase(String nombreProveedor) {
        return proveedorJPA.findByNombreProveedorContainingIgnoreCase(nombreProveedor);
    }

    @Override
    public List<Proveedor> findByActivoTrue() {
        return proveedorJPA.findByActivoTrue();
    }

    @Override
    public boolean existsByCif(String cif) {
        return proveedorJPA.existsByCif(cif);
    }
}