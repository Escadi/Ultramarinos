package com.example.backend.Repository.IMP;

import com.example.backend.Model.Compra;
import com.example.backend.Repository.Interface.CompraInterface;
import com.example.backend.Repository.JPA.CompraJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementacion de {@link CompraInterface} delegando en {@link CompraJPA}.
 */
@Repository
@RequiredArgsConstructor
public class CompraImpl implements CompraInterface {

    private final CompraJPA compraJPA;

    @Override
    public List<Compra> findAll() {
        return compraJPA.findAll();
    }

    @Override
    public Optional<Compra> findById(Long id) {
        return compraJPA.findById(id);
    }

    @Override
    public Compra save(Compra compra) {
        return compraJPA.save(compra);
    }

    @Override
    public void deleteById(Long id) {
        compraJPA.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return compraJPA.existsById(id);
    }

    @Override
    public long count() {
        return compraJPA.count();
    }

    @Override
    public Optional<Compra> findByNumeroCompra(String numeroCompra) {
        return compraJPA.findByNumeroCompra(numeroCompra);
    }

    @Override
    public List<Compra> findByEstado(String estado) {
        return compraJPA.findByEstado(estado);
    }

    @Override
    public List<Compra> findByEmpleadoId(Long empleadoId) {
        return compraJPA.findByEmpleadoId(empleadoId);
    }

    @Override
    public List<Compra> findByPedidoProveedorId(Long pedidoProveedorId) {
        return compraJPA.findByPedidoProveedorId(pedidoProveedorId);
    }

    @Override
    public List<Compra> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin) {
        return compraJPA.findByFechaBetween(inicio, fin);
    }
}