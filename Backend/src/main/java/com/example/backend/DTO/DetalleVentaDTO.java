package com.example.backend.DTO;

import com.example.backend.Model.DetalleVenta;
import lombok.*;

import java.math.BigDecimal;

/**
 * DTO de la entidad {@link DetalleVenta}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DetalleVentaDTO {

    private Long id;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal tipoIgic;
    private BigDecimal descuento;
    private BigDecimal subtotal;

    private Long ventaId;

    private Long productoId;
    private String productoNombre;
}