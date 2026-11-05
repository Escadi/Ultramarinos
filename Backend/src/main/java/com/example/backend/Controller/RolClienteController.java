package com.example.backend.Controller;

import com.example.backend.DTO.Mappers.RolClienteMapper;
import com.example.backend.DTO.RolClienteDTO;
import com.example.backend.Model.RolCliente;
import com.example.backend.Service.Service.RolClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rol-cliente")
@RequiredArgsConstructor
public class RolClienteController {

    private final RolClienteService rolClienteService;
    private final RolClienteMapper rolClienteMapper;

    @GetMapping
    public ResponseEntity<List<RolClienteDTO>> getAll() {
        List<RolCliente> list = rolClienteService.getAll();
        return ResponseEntity.ok(rolClienteMapper.toDtoList(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolClienteDTO> getById(@PathVariable Long id) {
        return rolClienteService.getById(id)
                .map(rolClienteMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RolClienteDTO> create(@RequestBody RolClienteDTO dto) {
        RolCliente entity = rolClienteMapper.toEntity(dto);
        RolCliente saved = rolClienteService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(rolClienteMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolClienteDTO> update(@PathVariable Long id, @RequestBody RolClienteDTO dto) {
        RolCliente entity = rolClienteMapper.toEntity(dto);
        RolCliente updated = rolClienteService.update(id, entity);
        return ResponseEntity.ok(rolClienteMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rolClienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

