package com.example.backend.Repository.JPA;

import com.example.backend.Model.RolTrabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio Spring Data JPA para {@link RolTrabajador}.
 */
@Repository
public interface RolTrabajadorJPA extends JpaRepository<RolTrabajador, Long> {


}