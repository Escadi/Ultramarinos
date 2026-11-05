package com.example.backend.Controller;

import com.example.backend.DTO.Mappers.TelefonoClienteMapper;
import com.example.backend.DTO.TelefonoClienteDTO;
import com.example.backend.Model.TelefonoCliente;
import com.example.backend.Service.Service.TelefonoClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/telefono-cliente")
@RequiredArgsConstructor
public class TelefonoClienteController {

    private final TelefonoClienteService telefonoClienteService;
    private final TelefonoClienteMapper telefonoClienteMapper;

    @GetMapping
    public ResponseEntity<List<TelefonoClienteDTO>> getAll() {
        List<TelefonoCliente> list = telefonoClienteService.getAll();
        return ResponseEntity.ok(telefonoClienteMapper.toDtoList(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelefonoClienteDTO> getById(@PathVariable Long id) {
        return telefonoClienteService.getById(id)
                .map(telefonoClienteMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<TelefonoClienteDTO>> getByClienteId(@PathVariable Long clienteId) {
        List<TelefonoCliente> list = telefonoClienteService.getByClienteId(clienteId);
        return ResponseEntity.ok(telefonoClienteMapper.toDtoList(list));
    }

    @PostMapping
    public ResponseEntity<TelefonoClienteDTO> create(@RequestBody TelefonoClienteDTO dto) {
        TelefonoCliente entity = telefonoClienteMapper.toEntity(dto);
        TelefonoCliente saved = telefonoClienteService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(telefonoClienteMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TelefonoClienteDTO> update(@PathVariable Long id, @RequestBody TelefonoClienteDTO dto) {
        TelefonoCliente entity = telefonoClienteMapper.toEntity(dto);
        TelefonoCliente updated = telefonoClienteService.update(id, entity);
        return ResponseEntity.ok(telefonoClienteMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        telefonoClienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

