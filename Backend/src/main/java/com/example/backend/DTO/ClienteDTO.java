package com.example.backend.DTO;

import com.example.backend.Model.Cliente;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private LocalDateTime fechaCreacion;
    private String correoElectronico;
    private Long rolClienteId;
    private String rolClienteTipo;

    /**
     * Convierte una entidad en su DTO.
     */
    public static ClienteDTO fromEntity(Cliente entity) {
        if (entity == null) {
            return null;
        }
        return ClienteDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .apellido(entity.getApellido())
                .fechaCreacion(entity.getFechaCreacion())
                .correoElectronico(entity.getCorreoElectronico())
                .rolClienteId(entity.getRolCliente() != null ? entity.getRolCliente().getId() : null)
                .rolClienteTipo(entity.getRolCliente() != null ? entity.getRolCliente().getTipoCliente() : null)
                .build();
    }

    /**
     * Convierte el DTO en una entidad (solo campos simples).
     * Las relaciones (rolCliente) deben asignarse en la capa de servicio.
     */
    public Cliente toEntity() {
        Cliente entity = new Cliente();
        entity.setId(this.id);
        if (this.nombre != null)
            entity.setNombre(this.nombre);
        if (this.apellido != null)
            entity.setApellido(this.apellido);
        if (this.fechaCreacion != null)
            entity.setFechaCreacion(this.fechaCreacion);
        if (this.correoElectronico != null)
            entity.setCorreoElectronico(this.correoElectronico);
        return entity;
    }
}