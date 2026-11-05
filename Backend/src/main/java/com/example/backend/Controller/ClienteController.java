package com.example.backend.Controller;

import com.example.backend.DTO.ClienteDTO;
import com.example.backend.DTO.Mappers.ClienteMapper;
import com.example.backend.DTO.RolClienteDTO;
import com.example.backend.Model.Cliente;
import com.example.backend.Model.RolCliente;
import com.example.backend.Service.Service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<ClienteDTO>> clienteList() {
        List<Cliente> clientes = clienteService.ListadoCliente();
        return ResponseEntity.ok(clienteMapper.toDtoList(clientes));
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Long id) {
        Cliente cliente = clienteService.getClienteById(id);
        return ResponseEntity.ok(clienteMapper.toDto(cliente));
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> addCliente(@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        Cliente saved = clienteService.addCliente(cliente);
        return ResponseEntity.ok(clienteMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> updateCliente(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        Cliente updated = clienteService.updateCliente(id, cliente);
        return ResponseEntity.ok(clienteMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }

    /** ROLCLIENTE ADAPTERS */
    @GetMapping("/rolCliente")
    @Transactional(readOnly = true)
    public ResponseEntity<List<RolClienteDTO>> rolClienteList() {
        List<RolCliente> rolClientes = clienteService.ListadoRolCliente();
        return ResponseEntity.ok(rolClientes.stream()
                .map(r -> new RolClienteDTO(r.getId(), r.getTipoCliente()))
                .toList());
    }
}
