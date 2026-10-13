package com.example.backend.Repository.IMP;

import com.example.backend.Model.Factura;
import com.example.backend.Repository.Interface.FacturaInterface;
import com.example.backend.Repository.JPA.FacturaJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementacion de {@link FacturaInterface} delegando en {@link FacturaJPA}.
 */
@Repository
@RequiredArgsConstructor
public class FacturaImpl implements FacturaInterface {

    private final FacturaJPA facturaJPA;

    @Override
    public List<Factura> findAll() {
        return facturaJPA.findAll();
    }

    @Override
    public Optional<Factura> findById(Long id) {
        return facturaJPA.findById(id);
    }

    @Override
    public Factura save(Factura factura) {
        return facturaJPA.save(factura);
    }

    @Override
    public void deleteById(Long id) {
        facturaJPA.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return facturaJPA.existsById(id);
    }

    @Override
    public long count() {
        return facturaJPA.count();
    }

    @Override
    public Optional<Factura> findByNumeroFactura(String numeroFactura) {
        return facturaJPA.findByNumeroFactura(numeroFactura);
    }

    @Override
    public Optional<Factura> findByVentaId(Long ventaId) {
        return facturaJPA.findByVentaId(ventaId);
    }

    @Override
    public List<Factura> findByClienteId(Long clienteId) {
        return facturaJPA.findByClienteId(clienteId);
    }

    @Override
    public List<Factura> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin) {
        return facturaJPA.findByFechaBetween(inicio, fin);
    }
}