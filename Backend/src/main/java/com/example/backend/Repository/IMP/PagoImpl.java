package com.example.backend.Repository.IMP;

import com.example.backend.Model.Pago;
import com.example.backend.Repository.Interface.PagoInterface;
import com.example.backend.Repository.JPA.PagoJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementacion de {@link PagoInterface} delegando en {@link PagoJPA}.
 */
@Repository
@RequiredArgsConstructor
public class PagoImpl implements PagoInterface {

    private final PagoJPA pagoJPA;

    @Override
    public List<Pago> findAll() {
        return pagoJPA.findAll();
    }

    @Override
    public Optional<Pago> findById(Long id) {
        return pagoJPA.findById(id);
    }

    @Override
    public Pago save(Pago pago) {
        return pagoJPA.save(pago);
    }

    @Override
    public void deleteById(Long id) {
        pagoJPA.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return pagoJPA.existsById(id);
    }

    @Override
    public long count() {
        return pagoJPA.count();
    }

    @Override
    public List<Pago> findByVentaId(Long ventaId) {
        return pagoJPA.findByVentaId(ventaId);
    }

    @Override
    public List<Pago> findByMetodo(String metodo) {
        return pagoJPA.findByMetodo(metodo);
    }
}