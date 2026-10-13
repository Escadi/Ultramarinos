package com.example.backend.Repository.Interface;

import com.example.backend.Model.Categoria;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio de {@link Categoria}.
 */
public interface CategoriaInterface {

    List<Categoria> findAll();

    Optional<Categoria> findById(Long id);

    Categoria save(Categoria categoria);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();

    Optional<Categoria> findByNombreCategoria(String nombreCategoria);

    boolean existsByNombreCategoria(String nombreCategoria);
}