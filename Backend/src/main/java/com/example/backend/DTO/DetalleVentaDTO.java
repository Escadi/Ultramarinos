package com.example.backend.DTO;

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

    private VentaDTO venta;
    private ProductoDTO producto;

}
