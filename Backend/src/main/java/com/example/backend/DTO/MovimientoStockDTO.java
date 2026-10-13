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

    /**
     * Convierte una entidad {@link MovimientoStock} en su DTO.
     */
    public MovimientoStockDTO fromEntity(MovimientoStock entity) {
        if (entity == null) {
            return null;
        }
        return MovimientoStockDTO.builder()
                .id(entity.getId())
                .fecha(entity.getFecha())
                .tipo(entity.getTipo())
                .cantidad(entity.getCantidad())
                .motivo(entity.getMotivo())
                .productoId(entity.getProducto() != null ? entity.getProducto().getId() : null)
                .productoNombre(entity.getProducto() != null ? entity.getProducto().getNombre() : null)
                .empleadoId(entity.getEmpleado() != null ? entity.getEmpleado().getId() : null)
                .empleadoNombre(entity.getEmpleado() != null ? entity.getEmpleado().getNombre() : null)
                .build();
    }

    /**
     * Convierte el DTO en una entidad {@link MovimientoStock} (solo campos simples).
     * Las relaciones (producto, empleado) deben asignarse en la capa de servicio.
     */
    public MovimientoStock toEntity() {
        MovimientoStock entity = new MovimientoStock();
        entity.setId(this.id);
        if (this.fecha != null) entity.setFecha(this.fecha);
        if (this.tipo != null) entity.setTipo(this.tipo);
        if (this.cantidad != null) entity.setCantidad(this.cantidad);
        if (this.motivo != null) entity.setMotivo(this.motivo);
        return entity;
    }
}