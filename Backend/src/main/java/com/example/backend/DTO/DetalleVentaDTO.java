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

    /**
     * Convierte una entidad {@link DetalleVenta} en su DTO.
     */
    public static DetalleVentaDTO fromEntity(DetalleVenta entity) {
        if (entity == null) {
            return null;
        }
        return DetalleVentaDTO.builder()
                .id(entity.getId())
                .cantidad(entity.getCantidad())
                .precioUnitario(entity.getPrecioUnitario())
                .tipoIgic(entity.getTipoIgic())
                .descuento(entity.getDescuento())
                .subtotal(entity.getSubtotal())
                .ventaId(entity.getVenta() != null ? entity.getVenta().getId() : null)
                .productoId(entity.getProducto() != null ? entity.getProducto().getId() : null)
                .productoNombre(entity.getProducto() != null ? entity.getProducto().getNombre() : null)
                .build();
    }

    /**
     * Convierte el DTO en una entidad {@link DetalleVenta} (solo campos simples).
     * Las relaciones (venta, producto) deben asignarse en la capa de servicio.
     */
    public DetalleVenta toEntity() {
        DetalleVenta entity = new DetalleVenta();
        entity.setId(this.id);
        if (this.cantidad != null) entity.setCantidad(this.cantidad);
        if (this.precioUnitario != null) entity.setPrecioUnitario(this.precioUnitario);
        if (this.tipoIgic != null) entity.setTipoIgic(this.tipoIgic);
        if (this.descuento != null) entity.setDescuento(this.descuento);
        if (this.subtotal != null) entity.setSubtotal(this.subtotal);
        return entity;
    }
}