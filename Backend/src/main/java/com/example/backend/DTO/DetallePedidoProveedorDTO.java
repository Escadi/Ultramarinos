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
public class DetallePedidoProveedorDTO {

    private Long id;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal descuento;
    private BigDecimal subtotal;

    private Long pedidoProveedorId;

    private Long productoProveedorId;

    /**
     * Convierte una entidad {@link DetallePedidoProveedor} en su DTO.
     */
    public DetallePedidoProveedorDTO fromEntity(DetallePedidoProveedor entity) {
        if (entity == null) {
            return null;
        }
        return DetallePedidoProveedorDTO.builder()
                .id(entity.getId())
                .cantidad(entity.getCantidad())
                .precioUnitario(entity.getPrecioUnitario())
                .descuento(entity.getDescuento())
                .subtotal(entity.getSubtotal())
                .pedidoProveedorId(entity.getPedidoProveedor() != null ? entity.getPedidoProveedor().getId() : null)
                .productoProveedorId(entity.getProductoProveedor() != null ? entity.getProductoProveedor().getId() : null)
                .build();
    }

    /**
     * Convierte el DTO en una entidad {@link DetallePedidoProveedor} (solo campos simples).
     * Las relaciones (pedidoProveedor, productoProveedor) deben asignarse en la capa de servicio.
     */
    public DetallePedidoProveedor toEntity() {
        DetallePedidoProveedor entity = new DetallePedidoProveedor();
        entity.setId(this.id);
        if (this.cantidad != null) entity.setCantidad(this.cantidad);
        if (this.precioUnitario != null) entity.setPrecioUnitario(this.precioUnitario);
        if (this.descuento != null) entity.setDescuento(this.descuento);
        if (this.subtotal != null) entity.setSubtotal(this.subtotal);
        return entity;
    }
}