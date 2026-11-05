package com.example.backend.Service.Service;

import com.example.backend.Model.Categoria;
import com.example.backend.Repository.CategoriaJPA;
import com.example.backend.Service.IMP.CategoriaServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class CategoriaService implements CategoriaServiceImpl {

    private final CategoriaJPA categoriaJPA;

    @Override
    public List<Categoria> getAll() {
        return categoriaJPA.findAll();
    }

    @Override
    public Optional<Categoria> getById(Long id) {
        return categoriaJPA.findById(id);
    }

    @Override
    public Categoria save(Categoria categoria) {
        return categoriaJPA.save(categoria);
    }

    @Override
    public Categoria update(Long id, Categoria categoria) {
        Categoria existing = categoriaJPA.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada con ID: " + id));
        existing.setNombreCategoria(categoria.getNombreCategoria());
        existing.setDescripcion(categoria.getDescripcion());
        return categoriaJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!categoriaJPA.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada con ID: " + id);
        }
        categoriaJPA.deleteById(id);
    }
}

