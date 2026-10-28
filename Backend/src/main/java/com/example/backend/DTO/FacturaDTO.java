package com.example.backend.DTO;

import com.example.backend.Model.Factura;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO de la entidad {@link Factura}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FacturaDTO {

    private Long id;
    private String numeroFactura;
    private LocalDateTime fecha;

    private Long clienteId;
    private String clienteNombre;

    private Long ventaId;
    private String ventaNumeroTicket;

}