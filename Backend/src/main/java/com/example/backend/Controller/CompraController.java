package com.example.backend.Controller;

import com.example.backend.DTO.CompraDTO;
import com.example.backend.DTO.Mappers.CompraMapper;
import com.example.backend.Model.Compra;
import com.example.backend.Service.Service.CompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compra")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;
    private final CompraMapper compraMapper;

    @GetMapping
    public ResponseEntity<List<CompraDTO>> getAll() {
        List<Compra> list = compraService.getAll();
        return ResponseEntity.ok(compraMapper.toDtoList(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraDTO> getById(@PathVariable Long id) {
        return compraService.getById(id)
                .map(compraMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CompraDTO> create(@RequestBody CompraDTO dto) {
        Compra entity = compraMapper.toEntity(dto);
        Compra saved = compraService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(compraMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompraDTO> update(@PathVariable Long id, @RequestBody CompraDTO dto) {
        Compra entity = compraMapper.toEntity(dto);
        Compra updated = compraService.update(id, entity);
        return ResponseEntity.ok(compraMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        compraService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

