package com.example.backend.Service.IMP;

import com.example.backend.DTO.CategoriaDTO;
import com.example.backend.Model.Categoria;
import com.example.backend.Repository.Interface.CategoriaInterface;
import com.example.backend.Service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaInterface categoriaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDTO> listar() {
        return categoriaRepository.findAll().stream().map(CategoriaDTO::fromEntity).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaDTO obtener(Long id) {
        return CategoriaDTO.fromEntity(buscar(id));
    }

    @Override
    public CategoriaDTO crear(CategoriaDTO dto) {
        if (dto.getNombreCategoria() == null || dto.getNombreCategoria().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de la categoria es obligatorio");
        }
        if (categoriaRepository.existsByNombreCategoria(dto.getNombreCategoria())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una categoria con ese nombre");
        }
        dto.setId(null);
        return CategoriaDTO.fromEntity(categoriaRepository.save(dto.toEntity()));
    }

    @Override
    public CategoriaDTO actualizar(Long id, CategoriaDTO dto) {
        Categoria existente = buscar(id);
        if (dto.getNombreCategoria() != null && !dto.getNombreCategoria().equals(existente.getNombreCategoria())) {
            if (categoriaRepository.existsByNombreCategoria(dto.getNombreCategoria())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una categoria con ese nombre");
            }
            existente.setNombreCategoria(dto.getNombreCategoria());
        }
        if (dto.getDescripcion() != null) {
            existente.setDescripcion(dto.getDescripcion());
        }
        return CategoriaDTO.fromEntity(categoriaRepository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada: " + id);
        }
        categoriaRepository.deleteById(id);
    }

    private Categoria buscar(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada: " + id));
    }
}
