package com.example.backend.DTO;

import com.example.backend.Model.Venta;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de la entidad {@link Venta}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VentaDTO {

    private Long id;
    private String numeroTicket;
    private LocalDateTime fecha;
    private BigDecimal total;
    private String estado;
    private BigDecimal baseImponible;
    private BigDecimal totalIgic;

    private Long clienteId;
    private String clienteNombre;

    private Long tpvId;
    private String tpvNombre;

    private Long turnoCajaId;

}