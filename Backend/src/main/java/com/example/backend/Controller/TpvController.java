package com.example.backend.Controller;

import com.example.backend.DTO.Mappers.TpvMapper;
import com.example.backend.DTO.TpvDTO;
import com.example.backend.Model.Tpv;
import com.example.backend.Service.Service.TpvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tpv")
@RequiredArgsConstructor
public class TpvController {

    private final TpvService tpvService;
    private final TpvMapper tpvMapper;

    @GetMapping
    public ResponseEntity<List<TpvDTO>> getAll() {
        List<Tpv> list = tpvService.getAll();
        return ResponseEntity.ok(tpvMapper.toDtoList(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TpvDTO> getById(@PathVariable Long id) {
        return tpvService.getById(id)
                .map(tpvMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TpvDTO> create(@RequestBody TpvDTO dto) {
        Tpv entity = tpvMapper.toEntity(dto);
        Tpv saved = tpvService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(tpvMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TpvDTO> update(@PathVariable Long id, @RequestBody TpvDTO dto) {
        Tpv entity = tpvMapper.toEntity(dto);
        Tpv updated = tpvService.update(id, entity);
        return ResponseEntity.ok(tpvMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tpvService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

