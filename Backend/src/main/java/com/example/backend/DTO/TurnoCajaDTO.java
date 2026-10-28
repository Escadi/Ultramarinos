package com.example.backend.DTO;

import com.example.backend.Model.TurnoCaja;
import lombok.*;

import java.math.BigDecimal;

/**
 * DTO de la entidad {@link TurnoCaja}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TurnoCajaDTO {

    private Long id;
    private BigDecimal fondoCaja;
    private String estadoCaja;
    private BigDecimal saldoReal;
    private BigDecimal saldoEsperado;
    private Long empleadoId;

}