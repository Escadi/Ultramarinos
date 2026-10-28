package com.example.backend.Repository.JPA;

import com.example.backend.Model.TelefonoCliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio Spring Data JPA para {@link TelefonoCliente}.
 */
public interface TelefonoClienteJPA extends JpaRepository<TelefonoCliente, Long> {

    List<TelefonoCliente> findByClienteId(Long clienteId);
}