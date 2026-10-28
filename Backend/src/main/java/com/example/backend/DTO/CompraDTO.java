package com.example.backend.DTO;

import com.example.backend.Model.Compra;
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

    private Long pedidoProveedorId;
    private String pedidoProveedorNumero;

    private Long empleadoId;
    private String empleadoNombre;
}