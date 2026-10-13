package com.example.backend.DTO;

import com.example.backend.Model.Pago;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de la entidad {@link Pago}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {

    private Long id;
    private String metodo;
    private BigDecimal importe;
    private LocalDateTime fechaPago;

    private Long ventaId;
    private String ventaNumeroTicket;

    /**
     * Convierte una entidad {@link Pago} en su DTO.
     */
    public PagoDTO fromEntity(Pago entity) {
        if (entity == null) {
            return null;
        }
        return PagoDTO.builder()
                .id(entity.getId())
                .metodo(entity.getMetodo())
                .importe(entity.getImporte())
                .fechaPago(entity.getFechaPago())
                .ventaId(entity.getVenta() != null ? entity.getVenta().getId() : null)
                .ventaNumeroTicket(entity.getVenta() != null ? entity.getVenta().getNumeroTicket() : null)
                .build();
    }

    /**
     * Convierte el DTO en una entidad {@link Pago} (solo campos simples).
     * Las relaciones (venta) deben asignarse en la capa de servicio.
     */
    public Pago toEntity() {
        Pago entity = new Pago();
        entity.setId(this.id);
        if (this.metodo != null) entity.setMetodo(this.metodo);
        if (this.importe != null) entity.setImporte(this.importe);
        if (this.fechaPago != null) entity.setFechaPago(this.fechaPago);
        return entity;
    }
}