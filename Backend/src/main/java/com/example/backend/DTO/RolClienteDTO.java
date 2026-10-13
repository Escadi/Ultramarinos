package com.example.backend.DTO;

import com.example.backend.Model.RolCliente;
import lombok.*;

/**
 * DTO de la entidad {@link RolCliente}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolClienteDTO {

    private Long id;
    private String tipoCliente;

    /**
     * Convierte una entidad {@link RolCliente} en su DTO.
     */
    public  RolClienteDTO fromEntity(RolCliente entity) {
        if (entity == null) {
            return null;
        }
        return RolClienteDTO.builder()
                .id(entity.getId())
                .tipoCliente(entity.getTipoCliente())
                .build();
    }

    /**
     * Convierte el DTO en una entidad {@link RolCliente} (solo campos simples).
     */
    public RolCliente toEntity() {
        RolCliente entity = new RolCliente();
        entity.setId(this.id);
        if (this.tipoCliente != null) entity.setTipoCliente(this.tipoCliente);
        return entity;
    }
}