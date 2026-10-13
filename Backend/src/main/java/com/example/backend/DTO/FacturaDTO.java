package com.example.backend.DTO;

import com.example.backend.Model.Factura;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO de la entidad {@link Factura}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacturaDTO {

    private Long id;
    private String numeroFactura;
    private LocalDateTime fecha;

    private Long clienteId;
    private String clienteNombre;

    private Long ventaId;
    private String ventaNumeroTicket;

    /**
     * Convierte una entidad {@link Factura} en su DTO.
     */
    public static FacturaDTO fromEntity(Factura entity) {
        if (entity == null) {
            return null;
        }
        return FacturaDTO.builder()
                .id(entity.getId())
                .numeroFactura(entity.getNumeroFactura())
                .fecha(entity.getFecha())
                .clienteId(entity.getCliente() != null ? entity.getCliente().getId() : null)
                .clienteNombre(entity.getCliente() != null ? entity.getCliente().getNombre() : null)
                .ventaId(entity.getVenta() != null ? entity.getVenta().getId() : null)
                .ventaNumeroTicket(entity.getVenta() != null ? entity.getVenta().getNumeroTicket() : null)
                .build();
    }

    /**
     * Convierte el DTO en una entidad {@link Factura} (solo campos simples).
     * Las relaciones (cliente, venta) deben asignarse en la capa de servicio.
     */
    public Factura toEntity() {
        Factura entity = new Factura();
        entity.setId(this.id);
        if (this.numeroFactura != null) entity.setNumeroFactura(this.numeroFactura);
        if (this.fecha != null) entity.setFecha(this.fecha);
        return entity;
    }
}