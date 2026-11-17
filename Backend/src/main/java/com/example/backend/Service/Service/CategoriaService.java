package com.example.backend.Service.Service;

import com.example.backend.Model.Categoria;
import com.example.backend.Repository.CategoriaJPA;
import com.example.backend.Service.IMP.CategoriaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaService implements CategoriaServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaService.class);

    private final CategoriaJPA categoriaJPA;

    @Override
    public List<Categoria> getAll() {
        logger.info("Obteniendo listado de todas las categorias");
        return categoriaJPA.findAll();
    }

    @Override
    public Optional<Categoria> getById(Long id) {
        logger.info("Buscando categoria por ID: {}", id);
        return categoriaJPA.findById(id);
    }

    @Override
    public Categoria save(Categoria categoria) {
        logger.info("Guardando nueva categoria: {}", categoria.getNombreCategoria());
        return categoriaJPA.save(categoria);
    }

    @Override
    public Categoria update(Long id, Categoria categoria) {
        logger.info("Actualizando categoria con ID: {}", id);
        Categoria existing = categoriaJPA.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Categoria no encontrada con ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada con ID: " + id);
                });
        existing.setNombreCategoria(categoria.getNombreCategoria());
        existing.setDescripcion(categoria.getDescripcion());
        return categoriaJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        logger.info("Eliminando categoria con ID: {}", id);
        if (!categoriaJPA.existsById(id)) {
            logger.warn("No se puede eliminar, categoria no encontrada con ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada con ID: " + id);
        }
        categoriaJPA.deleteById(id);
    }
}
