package com.example.backend.DTO;

import com.example.backend.Model.PedidoProveedor;
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
public class PedidoProveedorDTO {

    private Long id;
    private String numeroPedido;
    private LocalDateTime fechaPedido;
    private String estado;
    private BigDecimal totalEstimado;

    private Long empleadoId;
    private String empleadoNombre;

    /**
     * Convierte una entidad {@link PedidoProveedor} en su DTO.
     */
    public static PedidoProveedorDTO fromEntity(PedidoProveedor entity) {
        if (entity == null) {
            return null;
        }
        return PedidoProveedorDTO.builder()
                .id(entity.getId())
                .numeroPedido(entity.getNumeroPedido())
                .fechaPedido(entity.getFechaPedido())
                .estado(entity.getEstado())
                .totalEstimado(entity.getTotalEstimado())
                .empleadoId(entity.getEmpleado() != null ? entity.getEmpleado().getId() : null)
                .empleadoNombre(entity.getEmpleado() != null ? entity.getEmpleado().getNombre() : null)
                .build();
    }

    /**
     * Convierte el DTO en una entidad {@link PedidoProveedor} (solo campos simples).
     * Las relaciones (empleado) deben asignarse en la capa de servicio.
     */
    public PedidoProveedor toEntity() {
        PedidoProveedor entity = new PedidoProveedor();
        entity.setId(this.id);
        if (this.numeroPedido != null) entity.setNumeroPedido(this.numeroPedido);
        if (this.fechaPedido != null) entity.setFechaPedido(this.fechaPedido);
        if (this.estado != null) entity.setEstado(this.estado);
        if (this.totalEstimado != null) entity.setTotalEstimado(this.totalEstimado);
        return entity;
    }
}