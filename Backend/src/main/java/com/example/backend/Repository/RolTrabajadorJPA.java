package com.example.backend.Repository;

import com.example.backend.Model.RolTrabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio Spring Data JPA para {@link RolTrabajador}.
 */
@Repository
public interface RolTrabajadorJPA extends JpaRepository<RolTrabajador, Long> {


}