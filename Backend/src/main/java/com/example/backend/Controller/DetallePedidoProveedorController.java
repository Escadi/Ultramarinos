package com.example.backend.Controller;

import com.example.backend.DTO.DetallePedidoProveedorDTO;
import com.example.backend.DTO.Mappers.DetallePedidoProveedorMapper;
import com.example.backend.Model.DetallePedidoProveedor;
import com.example.backend.Service.Service.DetallePedidoProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-pedido-proveedor")
@RequiredArgsConstructor
public class DetallePedidoProveedorController {

    private final DetallePedidoProveedorService detallePedidoProveedorService;
    private final DetallePedidoProveedorMapper detallePedidoProveedorMapper;

    @GetMapping
    public ResponseEntity<List<DetallePedidoProveedorDTO>> getAll() {
        List<DetallePedidoProveedor> list = detallePedidoProveedorService.getAll();
        return ResponseEntity.ok(detallePedidoProveedorMapper.toDtoList(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoProveedorDTO> getById(@PathVariable Long id) {
        return detallePedidoProveedorService.getById(id)
                .map(detallePedidoProveedorMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<DetallePedidoProveedorDTO>> getByPedidoId(@PathVariable Long pedidoId) {
        List<DetallePedidoProveedor> list = detallePedidoProveedorService.getByPedidoProveedorId(pedidoId);
        return ResponseEntity.ok(detallePedidoProveedorMapper.toDtoList(list));
    }

    @PostMapping
    public ResponseEntity<DetallePedidoProveedorDTO> create(@RequestBody DetallePedidoProveedorDTO dto) {
        DetallePedidoProveedor entity = detallePedidoProveedorMapper.toEntity(dto);
        DetallePedidoProveedor saved = detallePedidoProveedorService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(detallePedidoProveedorMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedidoProveedorDTO> update(@PathVariable Long id, @RequestBody DetallePedidoProveedorDTO dto) {
        DetallePedidoProveedor entity = detallePedidoProveedorMapper.toEntity(dto);
        DetallePedidoProveedor updated = detallePedidoProveedorService.update(id, entity);
        return ResponseEntity.ok(detallePedidoProveedorMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        detallePedidoProveedorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

