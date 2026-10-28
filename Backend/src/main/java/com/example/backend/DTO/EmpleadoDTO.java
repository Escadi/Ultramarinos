package com.example.backend.DTO;

import com.example.backend.Model.Empleado;
import com.example.backend.Model.RolTrabajador;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * DTO de la entidad {@link Empleado}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmpleadoDTO {

    private Long id;
    private String codigoEmpleado;
    private String nombre;
    private String apellido;
    private String correoElectronico;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contrasenia;
    private Boolean activo;
    private RolTrabajador rolTrabajador;

}