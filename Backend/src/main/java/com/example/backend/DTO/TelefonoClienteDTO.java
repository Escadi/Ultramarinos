package com.example.backend.DTO;

import com.example.backend.Model.TelefonoCliente;
import lombok.*;

/**
 * DTO de la entidad {@link TelefonoCliente}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelefonoClienteDTO {

    private Long id;
    private String telefonoCliente;

    private Long clienteId;
    private String clienteNombre;

    /**
     * Convierte una entidad {@link TelefonoCliente} en su DTO.
     */
    public TelefonoClienteDTO fromEntity(TelefonoCliente entity) {
        if (entity == null) {
            return null;
        }
        return TelefonoClienteDTO.builder()
                .id(entity.getId())
                .telefonoCliente(entity.getTelefonoCliente())
                .clienteId(entity.getCliente() != null ? entity.getCliente().getId() : null)
                .clienteNombre(entity.getCliente() != null ? entity.getCliente().getNombre() : null)
                .build();
    }

    /**
     * Convierte el DTO en una entidad {@link TelefonoCliente} (solo campos simples).
     * Las relaciones (cliente) deben asignarse en la capa de servicio.
     */
    public TelefonoCliente toEntity() {
        TelefonoCliente entity = new TelefonoCliente();
        entity.setId(this.id);
        if (this.telefonoCliente != null) entity.setTelefonoCliente(this.telefonoCliente);
        return entity;
    }
}