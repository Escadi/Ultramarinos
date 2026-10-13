package com.example.backend.Repository.Interface;

import com.example.backend.Model.Proveedor;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio de {@link Proveedor}.
 */
public interface ProveedorInterface {

    List<Proveedor> findAll();

    Optional<Proveedor> findById(Long id);

    Proveedor save(Proveedor proveedor);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();

    Optional<Proveedor> findByCif(String cif);

    List<Proveedor> findByNombreProveedorContainingIgnoreCase(String nombreProveedor);

    List<Proveedor> findByActivoTrue();

    boolean existsByCif(String cif);
}