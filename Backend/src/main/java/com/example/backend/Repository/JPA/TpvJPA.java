package com.example.backend.Repository.JPA;

import com.example.backend.Model.Tpv;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio Spring Data JPA para {@link Tpv}.
 */
public interface TpvJPA extends JpaRepository<Tpv, Long> {

    List<Tpv> findByActivoTrue();
}