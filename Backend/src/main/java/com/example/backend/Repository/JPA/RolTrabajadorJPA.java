package com.example.backend.Repository;

import com.example.backend.Model.RolTrabajador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio Spring Data JPA para {@link RolTrabajador}.
 */
public interface RolTrabajadorJPA extends JpaRepository<RolTrabajador, Long> {

    Optional<RolTrabajador> findByNombreRol(String nombreRol);
}