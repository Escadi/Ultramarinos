package com.example.backend.Repository.IMP;

import com.example.backend.Model.Producto;
import com.example.backend.Repository.Interface.ProductoInterface;
import com.example.backend.Repository.JPA.ProductoJPA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductoImpl implements ProductoInterface {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Producto> findAll() {
        String jpql = "SELECT p FROM Producto p";
        return entityManager
                .createQuery(jpql, Producto.class)
                .getResultList();
    }

    @Override
    public Optional<Producto> findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Producto save(Producto producto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public boolean existsById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsById'");
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    @Override
    public Optional<Producto> findByCodigoProducto(String codigoProducto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByCodigoProducto'");
    }

    @Override
    public List<Producto> findByNombreContainingIgnoreCase(String nombre) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByNombreContainingIgnoreCase'");
    }

    @Override
    public List<Producto> findByCategoriaId(Long categoriaId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByCategoriaId'");
    }

    @Override
    public List<Producto> findByActivoTrue() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByActivoTrue'");
    }

    @Override
    public List<Producto> findProductosStockBajo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findProductosStockBajo'");
    }

    @Override
    public boolean existsByCodigoProducto(String codigoProducto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsByCodigoProducto'");
    }

}