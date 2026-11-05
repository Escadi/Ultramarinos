package com.example.backend.Controller;

import com.example.backend.DTO.DetalleCompraDTO;
import com.example.backend.DTO.Mappers.DetalleCompraMapper;
import com.example.backend.Model.DetalleCompra;
import com.example.backend.Service.Service.DetalleCompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-compra")
@RequiredArgsConstructor
public class DetalleCompraController {

    private final DetalleCompraService detalleCompraService;
    private final DetalleCompraMapper detalleCompraMapper;

    @GetMapping
    public ResponseEntity<List<DetalleCompraDTO>> getAll() {
        List<DetalleCompra> list = detalleCompraService.getAll();
        return ResponseEntity.ok(detalleCompraMapper.toDtoList(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleCompraDTO> getById(@PathVariable Long id) {
        return detalleCompraService.getById(id)
                .map(detalleCompraMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/compra/{compraId}")
    public ResponseEntity<List<DetalleCompraDTO>> getByCompraId(@PathVariable Long compraId) {
        List<DetalleCompra> list = detalleCompraService.getByCompraId(compraId);
        return ResponseEntity.ok(detalleCompraMapper.toDtoList(list));
    }

    @PostMapping
    public ResponseEntity<DetalleCompraDTO> create(@RequestBody DetalleCompraDTO dto) {
        DetalleCompra entity = detalleCompraMapper.toEntity(dto);
        DetalleCompra saved = detalleCompraService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalleCompraMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleCompraDTO> update(@PathVariable Long id, @RequestBody DetalleCompraDTO dto) {
        DetalleCompra entity = detalleCompraMapper.toEntity(dto);
        DetalleCompra updated = detalleCompraService.update(id, entity);
        return ResponseEntity.ok(detalleCompraMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        detalleCompraService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

