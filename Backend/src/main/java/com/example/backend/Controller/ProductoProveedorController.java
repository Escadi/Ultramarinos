package com.example.backend.Controller;

import com.example.backend.DTO.Mappers.ProductoProveedorMapper;
import com.example.backend.DTO.ProductoProveedorDTO;
import com.example.backend.Model.ProductoProveedor;
import com.example.backend.Service.Service.ProductoProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto-proveedor")
@RequiredArgsConstructor
public class ProductoProveedorController {

    private final ProductoProveedorService productoProveedorService;
    private final ProductoProveedorMapper productoProveedorMapper;

    @GetMapping
    public ResponseEntity<List<ProductoProveedorDTO>> getAll() {
        List<ProductoProveedor> list = productoProveedorService.getAll();
        return ResponseEntity.ok(productoProveedorMapper.toDtoList(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoProveedorDTO> getById(@PathVariable Long id) {
        return productoProveedorService.getById(id)
                .map(productoProveedorMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<ProductoProveedorDTO>> getByProductoId(@PathVariable Long productoId) {
        List<ProductoProveedor> list = productoProveedorService.getByProductoId(productoId);
        return ResponseEntity.ok(productoProveedorMapper.toDtoList(list));
    }

    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<List<ProductoProveedorDTO>> getByProveedorId(@PathVariable Long proveedorId) {
        List<ProductoProveedor> list = productoProveedorService.getByProveedorId(proveedorId);
        return ResponseEntity.ok(productoProveedorMapper.toDtoList(list));
    }

    @PostMapping
    public ResponseEntity<ProductoProveedorDTO> create(@RequestBody ProductoProveedorDTO dto) {
        ProductoProveedor entity = productoProveedorMapper.toEntity(dto);
        ProductoProveedor saved = productoProveedorService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoProveedorMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoProveedorDTO> update(@PathVariable Long id, @RequestBody ProductoProveedorDTO dto) {
        ProductoProveedor entity = productoProveedorMapper.toEntity(dto);
        ProductoProveedor updated = productoProveedorService.update(id, entity);
        return ResponseEntity.ok(productoProveedorMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productoProveedorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

