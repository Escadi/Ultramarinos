package com.example.backend.Controller;


import com.example.backend.DTO.ClienteDTO;
import com.example.backend.DTO.Mappers.ClienteMapper;
import com.example.backend.DTO.Mappers.RolClienteMapper;
import com.example.backend.DTO.RolClienteDTO;
import com.example.backend.Model.Cliente;
import com.example.backend.Model.RolCliente;
import com.example.backend.Service.Service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {

        private final ClienteService clienteService;
        private final ClienteMapper clienteMapper;
        private final RolClienteMapper  rolClienteMapper;

        @GetMapping
        @Transactional
        public ResponseEntity<List<ClienteDTO>> clienteList(){
                List<Cliente> clientes = clienteService.ListadoCliente();
            return ResponseEntity.ok(clienteMapper.toDtoList(clientes));

        }

        @PostMapping
        @Transactional
        public ResponseEntity<ClienteDTO> cliente(@RequestBody ClienteDTO clienteDTO){
                if(clienteDTO == null) {
                        return null;
                }
                Cliente entity = clienteMapper.toEntity(clienteDTO);
                Cliente save = clienteService.addCliente(entity);
                ClienteDTO respuestaDTO = clienteMapper.toDto(save);
                return ResponseEntity.status(HttpStatus.CREATED).body(respuestaDTO);
        }

        @PutMapping("{id}")
        public ResponseEntity<ClienteDTO> clienteUpdate(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO){
                if (id == null && clienteDTO == null) {
                        throw new IllegalArgumentException("El ID del cliente no pudo ser null");
                }
                Cliente entity = clienteMapper.toEntity(clienteDTO);
                Cliente update =  clienteService.updateCliente(id, entity);
                return ResponseEntity.ok(clienteMapper.toDto(update));
        }

        @DeleteMapping("{id}")
        public ResponseEntity<Void> clienteDelete(@PathVariable Long id){
                if(id == null ){
                    throw new IllegalArgumentException("El ID del cliente no existe");

                }
                clienteService.deleteCliente(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        /**
         * ROLCLIENT ADAPTERS
         * */

        @GetMapping("/rolCliente")
        @Transactional
        public ResponseEntity<List<RolClienteDTO>> rolClienteList(){
                List<RolCliente> rolClientes = clienteService.ListadoRolCliente();
                return ResponseEntity.ok(rolClienteMapper.toDtoList(rolClientes));
        }







}
