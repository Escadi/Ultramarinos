package com.example.backend.Controller;

import com.example.backend.DTO.Mappers.VentaMapper;
import com.example.backend.DTO.VentaDTO;
import com.example.backend.Model.Venta;
import com.example.backend.Service.Service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venta")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;
    private final VentaMapper ventaMapper;

    @GetMapping
    public ResponseEntity<List<VentaDTO>> getAll() {
        List<Venta> list = ventaService.getAll();
        return ResponseEntity.ok(ventaMapper.toDtoList(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> getById(@PathVariable Long id) {
        return ventaService.getById(id)
                .map(ventaMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VentaDTO> create(@RequestBody VentaDTO dto) {
        Venta entity = ventaMapper.toEntity(dto);
        Venta saved = ventaService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaDTO> update(@PathVariable Long id, @RequestBody VentaDTO dto) {
        Venta entity = ventaMapper.toEntity(dto);
        Venta updated = ventaService.update(id, entity);
        return ResponseEntity.ok(ventaMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ventaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

