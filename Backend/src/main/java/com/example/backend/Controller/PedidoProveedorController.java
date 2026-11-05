package com.example.backend.Controller;

import com.example.backend.DTO.Mappers.PedidoProveedorMapper;
import com.example.backend.DTO.PedidoProveedorDTO;
import com.example.backend.Model.PedidoProveedor;
import com.example.backend.Service.Service.PedidoProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedido-proveedor")
@RequiredArgsConstructor
public class PedidoProveedorController {

    private final PedidoProveedorService pedidoProveedorService;
    private final PedidoProveedorMapper pedidoProveedorMapper;

    @GetMapping
    public ResponseEntity<List<PedidoProveedorDTO>> getAll() {
        List<PedidoProveedor> list = pedidoProveedorService.getAll();
        return ResponseEntity.ok(pedidoProveedorMapper.toDtoList(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoProveedorDTO> getById(@PathVariable Long id) {
        return pedidoProveedorService.getById(id)
                .map(pedidoProveedorMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PedidoProveedorDTO> create(@RequestBody PedidoProveedorDTO dto) {
        PedidoProveedor entity = pedidoProveedorMapper.toEntity(dto);
        PedidoProveedor saved = pedidoProveedorService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoProveedorMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoProveedorDTO> update(@PathVariable Long id, @RequestBody PedidoProveedorDTO dto) {
        PedidoProveedor entity = pedidoProveedorMapper.toEntity(dto);
        PedidoProveedor updated = pedidoProveedorService.update(id, entity);
        return ResponseEntity.ok(pedidoProveedorMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pedidoProveedorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

