package com.example.backend.Controller;

import com.example.backend.DTO.Mappers.ProductoMapper;
import com.example.backend.DTO.ProductoDTO;
import com.example.backend.Model.Producto;
import com.example.backend.Service.Service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoMapper productoMapper;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAll() {
        List<Producto> productos = productoService.getAllProductos();
        return ResponseEntity.ok(productoMapper.toDtoList(productos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getById(@PathVariable Long id) {
        return productoService.getProductoById(id)
                .map(productoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<ProductoDTO> getByCodigo(@PathVariable String codigo) {
        Producto producto = productoService.getProductoByCodigoProducto(codigo);
        return ResponseEntity.ok(productoMapper.toDto(producto));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoDTO>> getByCategoria(@PathVariable String categoria) {
        List<Producto> productos = productoService.getProductosByCategoria(categoria);
        return ResponseEntity.ok(productoMapper.toDtoList(productos));
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> create(@RequestBody ProductoDTO dto) {
        Producto entity = productoMapper.toEntity(dto);
        Producto saved = productoService.addProducto(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> update(@PathVariable Long id, @RequestBody ProductoDTO dto) {
        Producto entity = productoMapper.toEntity(dto);
        Producto updated = productoService.updateProducto(id, entity);
        return ResponseEntity.ok(productoMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productoService.deleteProductoById(id);
        return ResponseEntity.noContent().build();
    }
}
