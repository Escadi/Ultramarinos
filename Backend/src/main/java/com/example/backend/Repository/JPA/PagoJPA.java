package com.example.backend.Repository.JPA;

import com.example.backend.Model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio Spring Data JPA para {@link Pago}.
 */
public interface PagoJPA extends JpaRepository<Pago, Long> {

    List<Pago> findByVentaId(Long ventaId);

    List<Pago> findByMetodo(String metodo);
}