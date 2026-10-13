package com.example.backend.Repository.Interface;

import com.example.backend.Model.Pago;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio de {@link Pago}.
 */
public interface PagoInterface {

    List<Pago> findAll();

    Optional<Pago> findById(Long id);

    Pago save(Pago pago);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();

    List<Pago> findByVentaId(Long ventaId);

    List<Pago> findByMetodo(String metodo);
}