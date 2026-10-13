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

    /**
     * Convierte una entidad {@link DetalleCompra} en su DTO.
     */
    public static DetalleCompraDTO fromEntity(DetalleCompra entity) {
        if (entity == null) {
            return null;
        }
        return DetalleCompraDTO.builder()
                .id(entity.getId())
                .cantidad(entity.getCantidad())
                .precioUnitario(entity.getPrecioUnitario())
                .tipoIgic(entity.getTipoIgic())
                .descuento(entity.getDescuento())
                .subtotal(entity.getSubtotal())
                .compraId(entity.getCompra() != null ? entity.getCompra().getId() : null)
                .productoId(entity.getProducto() != null ? entity.getProducto().getId() : null)
                .productoNombre(entity.getProducto() != null ? entity.getProducto().getNombre() : null)
                .build();
    }

    /**
     * Convierte el DTO en una entidad {@link DetalleCompra} (solo campos simples).
     * Las relaciones (compra, producto) deben asignarse en la capa de servicio.
     */
    public DetalleCompra toEntity() {
        DetalleCompra entity = new DetalleCompra();
        entity.setId(this.id);
        if (this.cantidad != null) entity.setCantidad(this.cantidad);
        if (this.precioUnitario != null) entity.setPrecioUnitario(this.precioUnitario);
        if (this.tipoIgic != null) entity.setTipoIgic(this.tipoIgic);
        if (this.descuento != null) entity.setDescuento(this.descuento);
        if (this.subtotal != null) entity.setSubtotal(this.subtotal);
        return entity;
    }
}