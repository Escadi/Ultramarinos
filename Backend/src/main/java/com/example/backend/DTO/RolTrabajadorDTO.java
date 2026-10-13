package com.example.backend.DTO;

import com.example.backend.Model.RolTrabajador;
import lombok.*;

/**
 * DTO de la entidad {@link RolTrabajador}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolTrabajadorDTO {

    private Long id;
    private String nombreRol;

    /**
     * Convierte una entidad {@link RolTrabajador} en su DTO.
     */
    public RolTrabajadorDTO fromEntity(RolTrabajador entity) {
        if (entity == null) {
            return null;
        }
        return RolTrabajadorDTO.builder()
                .id(entity.getId())
                .nombreRol(entity.getNombreRol())
                .build();
    }

    /**
     * Convierte el DTO en una entidad {@link RolTrabajador} (solo campos simples).
     */
    public RolTrabajador toEntity() {
        RolTrabajador entity = new RolTrabajador();
        entity.setId(this.id);
        if (this.nombreRol != null) entity.setNombreRol(this.nombreRol);
        return entity;
    }
}