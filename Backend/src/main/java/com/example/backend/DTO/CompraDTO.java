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

    /**
     * Convierte una entidad {@link Compra} en su DTO.
     */
    public static CompraDTO fromEntity(Compra entity) {
        if (entity == null) {
            return null;
        }
        return CompraDTO.builder()
                .id(entity.getId())
                .numeroCompra(entity.getNumeroCompra())
                .fecha(entity.getFecha())
                .total(entity.getTotal())
                .estado(entity.getEstado())
                .pedidoProveedorId(entity.getPedidoProveedor() != null ? entity.getPedidoProveedor().getId() : null)
                .pedidoProveedorNumero(entity.getPedidoProveedor() != null ? entity.getPedidoProveedor().getNumeroPedido() : null)
                .empleadoId(entity.getEmpleado() != null ? entity.getEmpleado().getId() : null)
                .empleadoNombre(entity.getEmpleado() != null ? entity.getEmpleado().getNombre() : null)
                .build();
    }

    /**
     * Convierte el DTO en una entidad {@link Compra} (solo campos simples).
     * Las relaciones (pedidoProveedor, empleado) deben asignarse en la capa de servicio.
     */
    public Compra toEntity() {
        Compra entity = new Compra();
        entity.setId(this.id);
        if (this.numeroCompra != null) entity.setNumeroCompra(this.numeroCompra);
        if (this.fecha != null) entity.setFecha(this.fecha);
        if (this.total != null) entity.setTotal(this.total);
        if (this.estado != null) entity.setEstado(this.estado);
        return entity;
    }
}