package com.example.backend.Controller;

import com.example.backend.DTO.Mappers.ProveedorMapper;
import com.example.backend.DTO.ProveedorDTO;
import com.example.backend.Model.Proveedor;
import com.example.backend.Service.Service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedor")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;
    private final ProveedorMapper proveedorMapper;

    @GetMapping
    public ResponseEntity<List<ProveedorDTO>> getAll() {
        List<Proveedor> list = proveedorService.getAll();
        return ResponseEntity.ok(proveedorMapper.toDtoList(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDTO> getById(@PathVariable Long id) {
        return proveedorService.getById(id)
                .map(proveedorMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProveedorDTO> create(@RequestBody ProveedorDTO dto) {
        Proveedor entity = proveedorMapper.toEntity(dto);
        Proveedor saved = proveedorService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDTO> update(@PathVariable Long id, @RequestBody ProveedorDTO dto) {
        Proveedor entity = proveedorMapper.toEntity(dto);
        Proveedor updated = proveedorService.update(id, entity);
        return ResponseEntity.ok(proveedorMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        proveedorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

