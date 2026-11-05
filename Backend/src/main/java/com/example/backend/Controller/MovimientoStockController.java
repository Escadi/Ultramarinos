package com.example.backend.Controller;

import com.example.backend.DTO.Mappers.MovimientoStockMapper;
import com.example.backend.DTO.MovimientoStockDTO;
import com.example.backend.Model.MovimientoStock;
import com.example.backend.Service.Service.MovimientoStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimiento-stock")
@RequiredArgsConstructor
public class MovimientoStockController {

    private final MovimientoStockService movimientoStockService;
    private final MovimientoStockMapper movimientoStockMapper;

    @GetMapping
    public ResponseEntity<List<MovimientoStockDTO>> getAll() {
        List<MovimientoStock> list = movimientoStockService.getAll();
        return ResponseEntity.ok(movimientoStockMapper.toDtoList(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoStockDTO> getById(@PathVariable Long id) {
        return movimientoStockService.getById(id)
                .map(movimientoStockMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<MovimientoStockDTO>> getByProductoId(@PathVariable Long productoId) {
        List<MovimientoStock> list = movimientoStockService.getByProductoId(productoId);
        return ResponseEntity.ok(movimientoStockMapper.toDtoList(list));
    }

    @PostMapping
    public ResponseEntity<MovimientoStockDTO> create(@RequestBody MovimientoStockDTO dto) {
        MovimientoStock entity = movimientoStockMapper.toEntity(dto);
        MovimientoStock saved = movimientoStockService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoStockMapper.toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movimientoStockService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

