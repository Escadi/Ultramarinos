package com.example.backend.DTO;

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
    private ClienteDTO cliente;
    private VentaDTO venta;

}
