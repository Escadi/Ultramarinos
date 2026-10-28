package com.example.backend.DTO;

import com.example.backend.Model.DetallePedidoProveedor;
import lombok.*;

import java.math.BigDecimal;

/**
 * DTO de la entidad {@link DetallePedidoProveedor}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DetallePedidoProveedorDTO {

    private Long id;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal descuento;
    private BigDecimal subtotal;

    private Long pedidoProveedorId;

    private Long productoProveedorId;
}