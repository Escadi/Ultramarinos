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
@Getter
@Setter
public class TpvDTO {

    private Long id;
    private String nombreTerminal;
    private Boolean activo;
    private String ubicacion;

}