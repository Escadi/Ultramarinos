package com.example.backend.Controller;

import com.example.backend.DTO.ProveedorDTO;
import com.example.backend.Service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    @GetMapping
    public List<ProveedorDTO> listar() {
        return proveedorService.listar();
    }

    @GetMapping("/{id}")
    public ProveedorDTO obtener(@PathVariable Long id) {
        return proveedorService.obtener(id);
    }

    @GetMapping("/buscar")
    public List<ProveedorDTO> buscar(@RequestParam String nombre) {
        return proveedorService.buscarPorNombre(nombre);
    }

    @GetMapping("/activos")
    public List<ProveedorDTO> activos() {
        return proveedorService.listarActivos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProveedorDTO crear(@RequestBody ProveedorDTO dto) {
        return proveedorService.crear(dto);
    }

    @PutMapping("/{id}")
    public ProveedorDTO actualizar(@PathVariable Long id, @RequestBody ProveedorDTO dto) {
        return proveedorService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        proveedorService.eliminar(id);
    }
}
