package com.example.backend.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
    private RolTrabajadorDTO rolTrabajador;

}
