package com.example.backend.DTO;

import com.example.backend.Model.Categoria;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {

    private Long id;
    private String nombreCategoria;
    private String descripcion;

    /**
     * Convierte una entidad en su DTO.
     */
    public CategoriaDTO fromEntity(Categoria entity) {
        if (entity == null) {
            return null;
        }
        return CategoriaDTO.builder()
                .id(entity.getId())
                .nombreCategoria(entity.getNombreCategoria())
                .descripcion(entity.getDescripcion())
                .build();
    }

    /**
     * Convierte el DTO en una entidad (solo campos simples).
     */
    public Categoria toEntity() {
        Categoria entity = new Categoria();
        entity.setId(this.id);
        if (this.nombreCategoria != null)
            entity.setNombreCategoria(this.nombreCategoria);
        if (this.descripcion != null)
            entity.setDescripcion(this.descripcion);
        return entity;
    }
}