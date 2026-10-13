package com.example.backend.Repository.IMP;

import com.example.backend.Model.DetalleCompra;
import com.example.backend.Repository.Interface.DetalleCompraInterface;
import com.example.backend.Repository.JPA.DetalleCompraJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementacion de {@link DetalleCompraInterface} delegando en {@link DetalleCompraJPA}.
 */
@Repository
@RequiredArgsConstructor
public class DetalleCompraImpl implements DetalleCompraInterface {

    private final DetalleCompraJPA detalleCompraJPA;

    @Override
    public List<DetalleCompra> findAll() {
        return detalleCompraJPA.findAll();
    }

    @Override
    public Optional<DetalleCompra> findById(Long id) {
        return detalleCompraJPA.findById(id);
    }

    @Override
    public DetalleCompra save(DetalleCompra detalleCompra) {
        return detalleCompraJPA.save(detalleCompra);
    }

    @Override
    public void deleteById(Long id) {
        detalleCompraJPA.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return detalleCompraJPA.existsById(id);
    }

    @Override
    public long count() {
        return detalleCompraJPA.count();
    }

    @Override
    public List<DetalleCompra> findByCompraId(Long compraId) {
        return detalleCompraJPA.findByCompraId(compraId);
    }

    @Override
    public List<DetalleCompra> findByProductoId(Long productoId) {
        return detalleCompraJPA.findByProductoId(productoId);
    }
}