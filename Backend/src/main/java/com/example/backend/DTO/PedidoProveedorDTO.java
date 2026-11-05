package com.example.backend.DTO;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de la entidad {@link PedidoProveedor}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PedidoProveedorDTO {

    private Long id;
    private String numeroPedido;
    private LocalDateTime fechaPedido;
    private String estado;
    private BigDecimal totalEstimado;
    private EmpleadoDTO empleado;

}
