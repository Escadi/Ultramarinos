package com.example.backend.Repository.JPA;

import com.example.backend.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio Spring Data JPA para {@link Cliente}.
 */
public interface ClienteJPA extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCorreoElectronico(String correoElectronico);

    List<Cliente> findByNombreContainingIgnoreCase(String nombre);

    List<Cliente> findByRolClienteId(Long rolClienteId);
}