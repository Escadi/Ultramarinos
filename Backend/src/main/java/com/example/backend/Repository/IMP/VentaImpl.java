package com.example.backend.Repository.IMP;

import com.example.backend.Model.Venta;
import com.example.backend.Repository.Interface.VentaInterface;
import com.example.backend.Repository.JPA.VentaJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementacion de {@link VentaInterface} delegando en {@link VentaJPA}.
 */
@Repository
@RequiredArgsConstructor
public class VentaImpl implements VentaInterface {

    private final VentaJPA ventaJPA;

    @Override
    public List<Venta> findAll() {
        return ventaJPA.findAll();
    }

    @Override
    public Optional<Venta> findById(Long id) {
        return ventaJPA.findById(id);
    }

    @Override
    public Venta save(Venta venta) {
        return ventaJPA.save(venta);
    }

    @Override
    public void deleteById(Long id) {
        ventaJPA.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return ventaJPA.existsById(id);
    }

    @Override
    public long count() {
        return ventaJPA.count();
    }

    @Override
    public Optional<Venta> findByNumeroTicket(String numeroTicket) {
        return ventaJPA.findByNumeroTicket(numeroTicket);
    }

    @Override
    public List<Venta> findByClienteId(Long clienteId) {
        return ventaJPA.findByClienteId(clienteId);
    }

    @Override
    public List<Venta> findByTpvId(Long tpvId) {
        return ventaJPA.findByTpvId(tpvId);
    }

    @Override
    public List<Venta> findByTurnoCajaId(Long turnoCajaId) {
        return ventaJPA.findByTurnoCajaId(turnoCajaId);
    }

    @Override
    public List<Venta> findByEstado(String estado) {
        return ventaJPA.findByEstado(estado);
    }

    @Override
    public List<Venta> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin) {
        return ventaJPA.findByFechaBetween(inicio, fin);
    }
}