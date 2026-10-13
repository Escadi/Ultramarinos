package com.example.backend.Repository.IMP;

import com.example.backend.Model.MovimientoStock;
import com.example.backend.Repository.Interface.MovimientoStockInterface;
import com.example.backend.Repository.JPA.MovimientoStockJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementacion de {@link MovimientoStockInterface} delegando en {@link MovimientoStockJPA}.
 */
@Repository
@RequiredArgsConstructor
public class MovimientoStockImpl implements MovimientoStockInterface {

    private final MovimientoStockJPA movimientoStockJPA;

    @Override
    public List<MovimientoStock> findAll() {
        return movimientoStockJPA.findAll();
    }

    @Override
    public Optional<MovimientoStock> findById(Long id) {
        return movimientoStockJPA.findById(id);
    }

    @Override
    public MovimientoStock save(MovimientoStock movimientoStock) {
        return movimientoStockJPA.save(movimientoStock);
    }

    @Override
    public void deleteById(Long id) {
        movimientoStockJPA.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return movimientoStockJPA.existsById(id);
    }

    @Override
    public long count() {
        return movimientoStockJPA.count();
    }

    @Override
    public List<MovimientoStock> findByProductoId(Long productoId) {
        return movimientoStockJPA.findByProductoId(productoId);
    }

    @Override
    public List<MovimientoStock> findByEmpleadoId(Long empleadoId) {
        return movimientoStockJPA.findByEmpleadoId(empleadoId);
    }

    @Override
    public List<MovimientoStock> findByTipo(String tipo) {
        return movimientoStockJPA.findByTipo(tipo);
    }

    @Override
    public List<MovimientoStock> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin) {
        return movimientoStockJPA.findByFechaBetween(inicio, fin);
    }
}