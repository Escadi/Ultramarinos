package com.example.backend.DTO;

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
