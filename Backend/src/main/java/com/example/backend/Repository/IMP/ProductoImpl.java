package com.example.backend.Repository.IMP;

import com.example.backend.Model.Producto;
import com.example.backend.Repository.Interface.ProductoInterface;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementacion de {@link ProductoInterface} usando JPQL con {@link EntityManager}.
 */
@Repository
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
        return Optional.ofNullable(entityManager.find(Producto.class, id));
    }

    @Override
    @Transactional
    public Producto save(Producto producto) {
        if (producto.getId() == null) {
            entityManager.persist(producto);
            return producto;
        }
        return entityManager.merge(producto);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Producto producto = entityManager.find(Producto.class, id);
        if (producto != null) {
            entityManager.remove(producto);
        }
    }

    @Override
    public boolean existsById(Long id) {
        String jpql = "SELECT COUNT(p) FROM Producto p WHERE p.id = :id";
        Long total = entityManager.createQuery(jpql, Long.class)
                .setParameter("id", id)
                .getSingleResult();
        return total > 0;
    }

    @Override
    public long count() {
        return entityManager
                .createQuery("SELECT COUNT(p) FROM Producto p", Long.class)
                .getSingleResult();
    }

    @Override
    public Optional<Producto> findByCodigoProducto(String codigoProducto) {
        String jpql = "SELECT p FROM Producto p WHERE p.codigoProducto = :codigo";
        return entityManager.createQuery(jpql, Producto.class)
                .setParameter("codigo", codigoProducto)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<Producto> findByNombreContainingIgnoreCase(String nombre) {
        String jpql = "SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))";
        return entityManager.createQuery(jpql, Producto.class)
                .setParameter("nombre", nombre)
                .getResultList();
    }

    @Override
    public List<Producto> findByCategoriaId(Long categoriaId) {
        String jpql = "SELECT p FROM Producto p WHERE p.categoria.id = :categoriaId";
        return entityManager.createQuery(jpql, Producto.class)
                .setParameter("categoriaId", categoriaId)
                .getResultList();
    }

    @Override
    public List<Producto> findByActivoTrue() {
        String jpql = "SELECT p FROM Producto p WHERE p.activo = true";
        return entityManager.createQuery(jpql, Producto.class)
                .getResultList();
    }

    @Override
    public List<Producto> findProductosStockBajo() {
        String jpql = "SELECT p FROM Producto p WHERE p.stockActual <= p.stockMinimo";
        return entityManager.createQuery(jpql, Producto.class)
                .getResultList();
    }

    @Override
    public boolean existsByCodigoProducto(String codigoProducto) {
        String jpql = "SELECT COUNT(p) FROM Producto p WHERE p.codigoProducto = :codigo";
        Long total = entityManager.createQuery(jpql, Long.class)
                .setParameter("codigo", codigoProducto)
                .getSingleResult();
        return total > 0;
    }
}