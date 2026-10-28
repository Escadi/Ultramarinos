package com.example.backend.DTO;

import com.example.backend.Model.MovimientoStock;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO de la entidad {@link MovimientoStock}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovimientoStockDTO {

    private Long id;
    private LocalDateTime fecha;
    private String tipo;
    private Integer cantidad;
    private String motivo;

    private Long productoId;
    private String productoNombre;

    private Long empleadoId;
    private String empleadoNombre;
    
}