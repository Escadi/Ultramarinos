package com.example.backend.Controller;

import com.example.backend.DTO.FacturaDTO;
import com.example.backend.DTO.Mappers.FacturaMapper;
import com.example.backend.Model.Factura;
import com.example.backend.Service.Service.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factura")
@RequiredArgsConstructor
public class FacturaController {

    private final FacturaService facturaService;
    private final FacturaMapper facturaMapper;

    @GetMapping
    public ResponseEntity<List<FacturaDTO>> getAll() {
        List<Factura> list = facturaService.getAll();
        return ResponseEntity.ok(facturaMapper.toDtoList(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaDTO> getById(@PathVariable Long id) {
        return facturaService.getById(id)
                .map(facturaMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FacturaDTO> create(@RequestBody FacturaDTO dto) {
        Factura entity = facturaMapper.toEntity(dto);
        Factura saved = facturaService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(facturaMapper.toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facturaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

