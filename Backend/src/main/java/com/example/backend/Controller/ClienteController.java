package com.example.backend.Controller;

import com.example.backend.DTO.ClienteDTO;
import com.example.backend.Service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public List<ClienteDTO> listar() {
        return clienteService.listar();
    }

    @GetMapping("/{id}")
    public ClienteDTO obtener(@PathVariable Long id) {
        return clienteService.obtener(id);
    }

    @GetMapping("/buscar")
    public List<ClienteDTO> buscar(@RequestParam String nombre) {
        return clienteService.buscarPorNombre(nombre);
    }

    @GetMapping("/rol/{rolClienteId}")
    public List<ClienteDTO> porRol(@PathVariable Long rolClienteId) {
        return clienteService.listarPorRol(rolClienteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO crear(@RequestBody ClienteDTO dto) {
        return clienteService.crear(dto);
    }

    @PutMapping("/{id}")
    public ClienteDTO actualizar(@PathVariable Long id, @RequestBody ClienteDTO dto) {
        return clienteService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
    }
}
