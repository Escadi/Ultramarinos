package com.example.backend.DTO;

import com.example.backend.Model.Pago;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de la entidad {@link Pago}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PagoDTO {

    private Long id;
    private String metodo;
    private BigDecimal importe;
    private LocalDateTime fechaPago;

    private Long ventaId;
    private String ventaNumeroTicket;
}