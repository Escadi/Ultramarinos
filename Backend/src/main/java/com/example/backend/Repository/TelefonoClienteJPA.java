package com.example.backend.Repository;

import com.example.backend.Model.TelefonoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio Spring Data JPA para {@link TelefonoCliente}.
 */
@Repository
public interface TelefonoClienteJPA extends JpaRepository<TelefonoCliente, Long> {

    List<TelefonoCliente> findByClienteId(Long clienteId);
}
