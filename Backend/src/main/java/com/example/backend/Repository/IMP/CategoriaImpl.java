package com.example.backend.Repository.IMP;

import com.example.backend.Model.Categoria;
import com.example.backend.Repository.Interface.CategoriaInterface;
import com.example.backend.Repository.JPA.CategoriaJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementacion de {@link CategoriaInterface} delegando en {@link CategoriaJPA}.
 */
@Repository
@RequiredArgsConstructor
public class CategoriaImpl implements CategoriaInterface {

    private final CategoriaJPA categoriaJPA;

    @Override
    public List<Categoria> findAll() {
        return categoriaJPA.findAll();
    }

    @Override
    public Optional<Categoria> findById(Long id) {
        return categoriaJPA.findById(id);
    }

    @Override
    public Categoria save(Categoria categoria) {
        return categoriaJPA.save(categoria);
    }

    @Override
    public void deleteById(Long id) {
        categoriaJPA.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return categoriaJPA.existsById(id);
    }

    @Override
    public long count() {
        return categoriaJPA.count();
    }

    @Override
    public Optional<Categoria> findByNombreCategoria(String nombreCategoria) {
        return categoriaJPA.findByNombreCategoria(nombreCategoria);
    }

    @Override
    public boolean existsByNombreCategoria(String nombreCategoria) {
        return categoriaJPA.existsByNombreCategoria(nombreCategoria);
    }
}