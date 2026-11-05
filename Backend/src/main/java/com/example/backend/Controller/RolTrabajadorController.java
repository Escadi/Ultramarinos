package com.example.backend.Controller;

import com.example.backend.DTO.Mappers.RoltrabajadorMapper;
import com.example.backend.DTO.RolTrabajadorDTO;
import com.example.backend.Model.RolTrabajador;
import com.example.backend.Service.Service.RolTrabajadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rol-trabajador")
@RequiredArgsConstructor
public class RolTrabajadorController {

    private final RolTrabajadorService rolTrabajadorService;
    private final RoltrabajadorMapper rolTrabajadorMapper;

    @GetMapping
    public ResponseEntity<List<RolTrabajadorDTO>> getAll() {
        List<RolTrabajador> list = rolTrabajadorService.getAll();
        return ResponseEntity.ok(rolTrabajadorMapper.toDtoList(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolTrabajadorDTO> getById(@PathVariable Long id) {
        return rolTrabajadorService.getById(id)
                .map(rolTrabajadorMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RolTrabajadorDTO> create(@RequestBody RolTrabajadorDTO dto) {
        RolTrabajador entity = rolTrabajadorMapper.toEntity(dto);
        RolTrabajador saved = rolTrabajadorService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(rolTrabajadorMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolTrabajadorDTO> update(@PathVariable Long id, @RequestBody RolTrabajadorDTO dto) {
        RolTrabajador entity = rolTrabajadorMapper.toEntity(dto);
        RolTrabajador updated = rolTrabajadorService.update(id, entity);
        return ResponseEntity.ok(rolTrabajadorMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rolTrabajadorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
