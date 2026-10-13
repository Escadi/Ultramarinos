package com.example.backend.Repository.JPA;

import com.example.backend.Model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio Spring Data JPA para {@link Proveedor}.
 */
public interface ProveedorJPA extends JpaRepository<Proveedor, Long> {

    Optional<Proveedor> findByCif(String cif);

    List<Proveedor> findByNombreProveedorContainingIgnoreCase(String nombreProveedor);

    List<Proveedor> findByActivoTrue();

    boolean existsByCif(String cif);
}