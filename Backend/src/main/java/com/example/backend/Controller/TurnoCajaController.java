package com.example.backend.Controller;

import com.example.backend.DTO.Mappers.TurnoCajaMapper;
import com.example.backend.DTO.TurnoCajaDTO;
import com.example.backend.Model.TurnoCaja;
import com.example.backend.Service.Service.TurnoCajaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turno-caja")
@RequiredArgsConstructor
public class TurnoCajaController {

    private final TurnoCajaService turnoCajaService;
    private final TurnoCajaMapper turnoCajaMapper;

    @GetMapping
    public ResponseEntity<List<TurnoCajaDTO>> getAll() {
        List<TurnoCaja> list = turnoCajaService.getAll();
        return ResponseEntity.ok(turnoCajaMapper.toDtoList(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoCajaDTO> getById(@PathVariable Long id) {
        return turnoCajaService.getById(id)
                .map(turnoCajaMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TurnoCajaDTO> create(@RequestBody TurnoCajaDTO dto) {
        TurnoCaja entity = turnoCajaMapper.toEntity(dto);
        TurnoCaja saved = turnoCajaService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoCajaMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurnoCajaDTO> update(@PathVariable Long id, @RequestBody TurnoCajaDTO dto) {
        TurnoCaja entity = turnoCajaMapper.toEntity(dto);
        TurnoCaja updated = turnoCajaService.update(id, entity);
        return ResponseEntity.ok(turnoCajaMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        turnoCajaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

