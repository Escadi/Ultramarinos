package com.example.backend.Repository.JPA;

import com.example.backend.Model.TurnoCaja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio Spring Data JPA para {@link TurnoCaja}.
 */
public interface TurnoCajaJPA extends JpaRepository<TurnoCaja, Long> {

    List<TurnoCaja> findByEmpleadoId(Long empleadoId);

    List<TurnoCaja> findByEstadoCaja(String estadoCaja);
}