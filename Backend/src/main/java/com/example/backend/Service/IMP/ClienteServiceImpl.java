package com.example.backend.Service.IMP;

import com.example.backend.DTO.ClienteDTO;
import com.example.backend.Model.Cliente;
import com.example.backend.Model.RolCliente;
import com.example.backend.Repository.Interface.ClienteInterface;
import com.example.backend.Repository.Interface.RolClienteInterface;
import com.example.backend.Service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final ClienteInterface clienteRepository;
    private final RolClienteInterface rolClienteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> listar() {
        return mapear(clienteRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteDTO obtener(Long id) {
        return ClienteDTO.fromEntity(buscar(id));
    }

    @Override
    public ClienteDTO crear(ClienteDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del cliente es obligatorio");
        }
        validarCorreoUnico(dto.getCorreoElectronico(), null);
        dto.setId(null);
        Cliente cliente = dto.toEntity();
        if (dto.getRolClienteId() != null) {
            cliente.setRolCliente(buscarRol(dto.getRolClienteId()));
        }
        return ClienteDTO.fromEntity(clienteRepository.save(cliente));
    }

    @Override
    public ClienteDTO actualizar(Long id, ClienteDTO dto) {
        Cliente existente = buscar(id);

        if (dto.getNombre() != null) existente.setNombre(dto.getNombre());
        if (dto.getApellido() != null) existente.setApellido(dto.getApellido());
        if (dto.getCorreoElectronico() != null
                && !dto.getCorreoElectronico().equals(existente.getCorreoElectronico())) {
            validarCorreoUnico(dto.getCorreoElectronico(), id);
            existente.setCorreoElectronico(dto.getCorreoElectronico());
        }
        if (dto.getRolClienteId() != null) {
            existente.setRolCliente(buscarRol(dto.getRolClienteId()));
        }
        return ClienteDTO.fromEntity(clienteRepository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado: " + id);
        }
        clienteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> buscarPorNombre(String nombre) {
        return mapear(clienteRepository.findByNombreContainingIgnoreCase(nombre));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> listarPorRol(Long rolClienteId) {
        return mapear(clienteRepository.findByRolClienteId(rolClienteId));
    }

    private void validarCorreoUnico(String correo, Long idActual) {
        if (correo == null || correo.isBlank()) {
            return;
        }
        clienteRepository.findByCorreoElectronico(correo).ifPresent(otro -> {
            if (!otro.getId().equals(idActual)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un cliente con ese correo");
            }
        });
    }

    private List<ClienteDTO> mapear(List<Cliente> clientes) {
        return clientes.stream().map(ClienteDTO::fromEntity).toList();
    }

    private Cliente buscar(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado: " + id));
    }

    private RolCliente buscarRol(Long rolId) {
        return rolClienteRepository.findById(rolId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Rol de cliente no encontrado: " + rolId));
    }
}
