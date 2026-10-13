package com.example.backend.DTO;

import com.example.backend.Model.Venta;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de la entidad {@link Venta}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VentaDTO {

    private Long id;
    private String numeroTicket;
    private LocalDateTime fecha;
    private BigDecimal total;
    private String estado;
    private BigDecimal baseImponible;
    private BigDecimal totalIgic;

    private Long clienteId;
    private String clienteNombre;

    private Long tpvId;
    private String tpvNombre;

    private Long turnoCajaId;

    /**
     * Convierte una entidad {@link Venta} en su DTO.
     */
    public VentaDTO fromEntity(Venta entity) {
        if (entity == null) {
            return null;
        }
        return VentaDTO.builder()
                .id(entity.getId())
                .numeroTicket(entity.getNumeroTicket())
                .fecha(entity.getFecha())
                .total(entity.getTotal())
                .estado(entity.getEstado())
                .baseImponible(entity.getBaseImponible())
                .totalIgic(entity.getTotalIgic())
                .clienteId(entity.getCliente() != null ? entity.getCliente().getId() : null)
                .clienteNombre(entity.getCliente() != null ? entity.getCliente().getNombre() : null)
                .tpvId(entity.getTpv() != null ? entity.getTpv().getId() : null)
                .tpvNombre(entity.getTpv() != null ? entity.getTpv().getNombreTerminal() : null)
                .turnoCajaId(entity.getTurnoCaja() != null ? entity.getTurnoCaja().getId() : null)
                .build();
    }

    /**
     * Convierte el DTO en una entidad {@link Venta} (solo campos simples).
     * Las relaciones (cliente, tpv, turnoCaja) deben asignarse en la capa de servicio.
     */
    public Venta toEntity() {
        Venta entity = new Venta();
        entity.setId(this.id);
        if (this.numeroTicket != null) entity.setNumeroTicket(this.numeroTicket);
        if (this.fecha != null) entity.setFecha(this.fecha);
        if (this.total != null) entity.setTotal(this.total);
        if (this.estado != null) entity.setEstado(this.estado);
        if (this.baseImponible != null) entity.setBaseImponible(this.baseImponible);
        if (this.totalIgic != null) entity.setTotalIgic(this.totalIgic);
        return entity;
    }
}