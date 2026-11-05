package com.example.backend.Controller;

import com.example.backend.DTO.CategoriaDTO;
import com.example.backend.DTO.Mappers.CategoriaMapper;
import com.example.backend.Model.Categoria;
import com.example.backend.Service.Service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final CategoriaMapper categoriaMapper;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getAll() {
        List<Categoria> list = categoriaService.getAll();
        return ResponseEntity.ok(categoriaMapper.toDtoList(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> getById(@PathVariable Long id) {
        return categoriaService.getById(id)
                .map(categoriaMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> create(@RequestBody CategoriaDTO dto) {
        Categoria entity = categoriaMapper.toEntity(dto);
        Categoria saved = categoriaService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> update(@PathVariable Long id, @RequestBody CategoriaDTO dto) {
        Categoria entity = categoriaMapper.toEntity(dto);
        Categoria updated = categoriaService.update(id, entity);
        return ResponseEntity.ok(categoriaMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
