package com.example.backend.DTO;

import com.example.backend.Model.DetalleCompra;
import lombok.*;

import java.math.BigDecimal;

/**
 * DTO de la entidad {@link DetalleCompra}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DetalleCompraDTO {

    private Long id;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal tipoIgic;
    private BigDecimal descuento;
    private BigDecimal subtotal;

    private Long compraId;

    private Long productoId;
    private String productoNombre;

}