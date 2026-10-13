package com.example.backend.DTO;

import com.example.backend.Model.Proveedor;
import lombok.*;

/**
 * DTO de la entidad {@link Proveedor}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorDTO {

    private Long id;
    private String cif;
    private String contacto;
    private String nombreProveedor;
    private Boolean activo;
    private String email;
    private String telefono;

    /**
     * Convierte una entidad {@link Proveedor} en su DTO.
     */
    public static ProveedorDTO fromEntity(Proveedor entity) {
        if (entity == null) {
            return null;
        }
        return ProveedorDTO.builder()
                .id(entity.getId())
                .cif(entity.getCif())
                .contacto(entity.getContacto())
                .nombreProveedor(entity.getNombreProveedor())
                .activo(entity.getActivo())
                .email(entity.getEmail())
                .telefono(entity.getTelefono())
                .build();
    }

    /**
     * Convierte el DTO en una entidad {@link Proveedor} (solo campos simples).
     */
    public Proveedor toEntity() {
        Proveedor entity = new Proveedor();
        entity.setId(this.id);
        if (this.cif != null) entity.setCif(this.cif);
        if (this.contacto != null) entity.setContacto(this.contacto);
        if (this.nombreProveedor != null) entity.setNombreProveedor(this.nombreProveedor);
        if (this.activo != null) entity.setActivo(this.activo);
        if (this.email != null) entity.setEmail(this.email);
        if (this.telefono != null) entity.setTelefono(this.telefono);
        return entity;
    }
}