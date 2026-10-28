package com.example.backend.DTO;

import com.example.backend.Model.RolTrabajador;
import lombok.*;

import java.util.List;

/**
 * DTO de la entidad {@link RolTrabajador}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RolTrabajadorDTO {

    private Long id;
    private String nombreRol;

}