package com.example.backend.Service.IMP;

import com.example.backend.DTO.ProveedorDTO;
import com.example.backend.Model.Proveedor;
import com.example.backend.Repository.Interface.ProveedorInterface;
import com.example.backend.Service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorInterface proveedorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProveedorDTO> listar() {
        return mapear(proveedorRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public ProveedorDTO obtener(Long id) {
        return ProveedorDTO.fromEntity(buscar(id));
    }

    @Override
    public ProveedorDTO crear(ProveedorDTO dto) {
        if (dto.getCif() == null || dto.getCif().isBlank()
                || dto.getNombreProveedor() == null || dto.getNombreProveedor().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CIF y nombre del proveedor son obligatorios");
        }
        if (proveedorRepository.existsByCif(dto.getCif())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un proveedor con ese CIF");
        }
        dto.setId(null);
        return ProveedorDTO.fromEntity(proveedorRepository.save(dto.toEntity()));
    }

    @Override
    public ProveedorDTO actualizar(Long id, ProveedorDTO dto) {
        Proveedor existente = buscar(id);

        if (dto.getCif() != null && !dto.getCif().equals(existente.getCif())) {
            if (proveedorRepository.existsByCif(dto.getCif())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un proveedor con ese CIF");
            }
            existente.setCif(dto.getCif());
        }
        if (dto.getNombreProveedor() != null) existente.setNombreProveedor(dto.getNombreProveedor());
        if (dto.getContacto() != null) existente.setContacto(dto.getContacto());
        if (dto.getActivo() != null) existente.setActivo(dto.getActivo());
        if (dto.getEmail() != null) existente.setEmail(dto.getEmail());
        if (dto.getTelefono() != null) existente.setTelefono(dto.getTelefono());

        return ProveedorDTO.fromEntity(proveedorRepository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        if (!proveedorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado: " + id);
        }
        proveedorRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProveedorDTO> buscarPorNombre(String nombre) {
        return mapear(proveedorRepository.findByNombreProveedorContainingIgnoreCase(nombre));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProveedorDTO> listarActivos() {
        return mapear(proveedorRepository.findByActivoTrue());
    }

    private List<ProveedorDTO> mapear(List<Proveedor> proveedores) {
        return proveedores.stream().map(ProveedorDTO::fromEntity).toList();
    }

    private Proveedor buscar(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado: " + id));
    }
}
