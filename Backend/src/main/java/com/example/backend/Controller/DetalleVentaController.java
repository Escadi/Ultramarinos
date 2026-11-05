package com.example.backend.Controller;

import com.example.backend.DTO.DetalleVentaDTO;
import com.example.backend.DTO.Mappers.DetalleVentaMapper;
import com.example.backend.Model.DetalleVenta;
import com.example.backend.Service.Service.DetalleVentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-venta")
@RequiredArgsConstructor
public class DetalleVentaController {

    private final DetalleVentaService detalleVentaService;
    private final DetalleVentaMapper detalleVentaMapper;

    @GetMapping
    public ResponseEntity<List<DetalleVentaDTO>> getAll() {
        List<DetalleVenta> list = detalleVentaService.getAll();
        return ResponseEntity.ok(detalleVentaMapper.toDtoList(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleVentaDTO> getById(@PathVariable Long id) {
        return detalleVentaService.getById(id)
                .map(detalleVentaMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/venta/{ventaId}")
    public ResponseEntity<List<DetalleVentaDTO>> getByVentaId(@PathVariable Long ventaId) {
        List<DetalleVenta> list = detalleVentaService.getByVentaId(ventaId);
        return ResponseEntity.ok(detalleVentaMapper.toDtoList(list));
    }

    @PostMapping
    public ResponseEntity<DetalleVentaDTO> create(@RequestBody DetalleVentaDTO dto) {
        DetalleVenta entity = detalleVentaMapper.toEntity(dto);
        DetalleVenta saved = detalleVentaService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalleVentaMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleVentaDTO> update(@PathVariable Long id, @RequestBody DetalleVentaDTO dto) {
        DetalleVenta entity = detalleVentaMapper.toEntity(dto);
        DetalleVenta updated = detalleVentaService.update(id, entity);
        return ResponseEntity.ok(detalleVentaMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        detalleVentaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

