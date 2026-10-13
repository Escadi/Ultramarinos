package com.example.backend.Repository.IMP;

import com.example.backend.Model.DetalleVenta;
import com.example.backend.Repository.Interface.DetalleVentaInterface;
import com.example.backend.Repository.JPA.DetalleVentaJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementacion de {@link DetalleVentaInterface} delegando en {@link DetalleVentaJPA}.
 */
@Repository
@RequiredArgsConstructor
public class DetalleVentaImpl implements DetalleVentaInterface {

    private final DetalleVentaJPA detalleVentaJPA;

    @Override
    public List<DetalleVenta> findAll() {
        return detalleVentaJPA.findAll();
    }

    @Override
    public Optional<DetalleVenta> findById(Long id) {
        return detalleVentaJPA.findById(id);
    }

    @Override
    public DetalleVenta save(DetalleVenta detalleVenta) {
        return detalleVentaJPA.save(detalleVenta);
    }

    @Override
    public void deleteById(Long id) {
        detalleVentaJPA.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return detalleVentaJPA.existsById(id);
    }

    @Override
    public long count() {
        return detalleVentaJPA.count();
    }

    @Override
    public List<DetalleVenta> findByVentaId(Long ventaId) {
        return detalleVentaJPA.findByVentaId(ventaId);
    }

    @Override
    public List<DetalleVenta> findByProductoId(Long productoId) {
        return detalleVentaJPA.findByProductoId(productoId);
    }
}