package com.example.backend.DTO;

import com.example.backend.Model.Empleado;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * DTO de la entidad {@link Empleado}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoDTO {

    private Long id;
    private String codigoEmpleado;
    private String nombre;
    private String apellido;
    private String correoElectronico;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contrasenia;
    private Boolean activo;

    private Long rolTrabajadorId;
    private String rolTrabajadorNombre;

    /**
     * Convierte una entidad {@link Empleado} en su DTO.
     */
    public static EmpleadoDTO fromEntity(Empleado entity) {
        if (entity == null) {
            return null;
        }
        return EmpleadoDTO.builder()
                .id(entity.getId())
                .codigoEmpleado(entity.getCodigoEmpleado())
                .nombre(entity.getNombre())
                .apellido(entity.getApellido())
                .correoElectronico(entity.getCorreoElectronico())
                .activo(entity.getActivo())
                .rolTrabajadorId(entity.getRolTrabajador() != null ? entity.getRolTrabajador().getId() : null)
                .rolTrabajadorNombre(entity.getRolTrabajador() != null ? entity.getRolTrabajador().getNombreRol() : null)
                .build();
    }

    /**
     * Convierte el DTO en una entidad {@link Empleado} (solo campos simples).
     * Las relaciones (rolTrabajador) deben asignarse en la capa de servicio.
     */
    public Empleado toEntity() {
        Empleado entity = new Empleado();
        entity.setId(this.id);
        if (this.codigoEmpleado != null) entity.setCodigoEmpleado(this.codigoEmpleado);
        if (this.nombre != null) entity.setNombre(this.nombre);
        if (this.apellido != null) entity.setApellido(this.apellido);
        if (this.correoElectronico != null) entity.setCorreoElectronico(this.correoElectronico);
        if (this.contrasenia != null) entity.setContrasenia(this.contrasenia);
        if (this.activo != null) entity.setActivo(this.activo);
        return entity;
    }
}