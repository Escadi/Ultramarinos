package com.example.backend.Repository.Interface;

import com.example.backend.Model.Producto;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio de {@link Producto}.
 */
public interface ProductoInterface {

    List<Producto> findAll();

    Optional<Producto> findById(Long id);

    Producto save(Producto producto);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();

    Optional<Producto> findByCodigoProducto(String codigoProducto);

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByCategoriaId(Long categoriaId);

    List<Producto> findByActivoTrue();

    List<Producto> findProductosStockBajo();

    boolean existsByCodigoProducto(String codigoProducto);
}