package com.example.backend.DTO;

import com.example.backend.Model.TurnoCaja;
import lombok.*;

import java.math.BigDecimal;

/**
 * DTO de la entidad {@link TurnoCaja}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TurnoCajaDTO {

    private Long id;
    private BigDecimal fondoCaja;
    private String estadoCaja;
    private BigDecimal saldoReal;
    private BigDecimal saldoEsperado;

    private Long empleadoId;
    private String empleadoNombre;

    /**
     * Convierte una entidad {@link TurnoCaja} en su DTO.
     */
    public  TurnoCajaDTO fromEntity(TurnoCaja entity) {
        if (entity == null) {
            return null;
        }
        return TurnoCajaDTO.builder()
                .id(entity.getId())
                .fondoCaja(entity.getFondoCaja())
                .estadoCaja(entity.getEstadoCaja())
                .saldoReal(entity.getSaldoReal())
                .saldoEsperado(entity.getSaldoEsperado())
                .empleadoId(entity.getEmpleado() != null ? entity.getEmpleado().getId() : null)
                .empleadoNombre(entity.getEmpleado() != null ? entity.getEmpleado().getNombre() : null)
                .build();
    }

    /**
     * Convierte el DTO en una entidad {@link TurnoCaja} (solo campos simples).
     * Las relaciones (empleado) deben asignarse en la capa de servicio.
     */
    public TurnoCaja toEntity() {
        TurnoCaja entity = new TurnoCaja();
        entity.setId(this.id);
        if (this.fondoCaja != null) entity.setFondoCaja(this.fondoCaja);
        if (this.estadoCaja != null) entity.setEstadoCaja(this.estadoCaja);
        if (this.saldoReal != null) entity.setSaldoReal(this.saldoReal);
        if (this.saldoEsperado != null) entity.setSaldoEsperado(this.saldoEsperado);
        return entity;
    }
}