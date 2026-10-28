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
@Getter
@Setter
public class ProveedorDTO {

    private Long id;
    private String cif;
    private String contacto;
    private String nombreProveedor;
    private Boolean activo;
    private String email;
    private String telefono;

}