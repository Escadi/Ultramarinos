package com.example.backend.DTO;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de la entidad {@link Compra}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompraDTO {

    private Long id;
    private String numeroCompra;
    private LocalDateTime fecha;
    private BigDecimal total;
    private String estado;

    private PedidoProveedorDTO pedidoProveedor;
    private EmpleadoDTO empleado;

}
