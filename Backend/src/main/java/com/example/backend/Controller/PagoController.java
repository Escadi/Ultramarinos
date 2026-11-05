package com.example.backend.Controller;

import com.example.backend.DTO.Mappers.PagoMapper;
import com.example.backend.DTO.PagoDTO;
import com.example.backend.Model.Pago;
import com.example.backend.Service.Service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pago")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;
    private final PagoMapper pagoMapper;

    @GetMapping
    public ResponseEntity<List<PagoDTO>> getAll() {
        List<Pago> list = pagoService.getAll();
        return ResponseEntity.ok(pagoMapper.toDtoList(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> getById(@PathVariable Long id) {
        return pagoService.getById(id)
                .map(pagoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/venta/{ventaId}")
    public ResponseEntity<List<PagoDTO>> getByVentaId(@PathVariable Long ventaId) {
        List<Pago> list = pagoService.getByVentaId(ventaId);
        return ResponseEntity.ok(pagoMapper.toDtoList(list));
    }

    @PostMapping
    public ResponseEntity<PagoDTO> create(@RequestBody PagoDTO dto) {
        Pago entity = pagoMapper.toEntity(dto);
        Pago saved = pagoService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoDTO> update(@PathVariable Long id, @RequestBody PagoDTO dto) {
        Pago entity = pagoMapper.toEntity(dto);
        Pago updated = pagoService.update(id, entity);
        return ResponseEntity.ok(pagoMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pagoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

