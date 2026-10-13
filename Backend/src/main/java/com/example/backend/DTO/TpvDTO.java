package com.example.backend.DTO;

import com.example.backend.Model.Tpv;
import lombok.*;

/**
 * DTO de la entidad {@link Tpv}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TpvDTO {

    private Long id;
    private String nombreTerminal;
    private Boolean activo;
    private String ubicacion;

    /**
     * Convierte una entidad {@link Tpv} en su DTO.
     */
    public static TpvDTO fromEntity(Tpv entity) {
        if (entity == null) {
            return null;
        }
        return TpvDTO.builder()
                .id(entity.getId())
                .nombreTerminal(entity.getNombreTerminal())
                .activo(entity.getActivo())
                .ubicacion(entity.getUbicacion())
                .build();
    }

    /**
     * Convierte el DTO en una entidad {@link Tpv} (solo campos simples).
     */
    public Tpv toEntity() {
        Tpv entity = new Tpv();
        entity.setId(this.id);
        if (this.nombreTerminal != null) entity.setNombreTerminal(this.nombreTerminal);
        if (this.activo != null) entity.setActivo(this.activo);
        if (this.ubicacion != null) entity.setUbicacion(this.ubicacion);
        return entity;
    }
}