package com.example.backend.Repository.JPA;

import com.example.backend.Model.RolCliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio Spring Data JPA para {@link RolCliente}.
 */
public interface RolClienteJPA extends JpaRepository<RolCliente, Long> {

    Optional<RolCliente> findByTipoCliente(String tipoCliente);
}