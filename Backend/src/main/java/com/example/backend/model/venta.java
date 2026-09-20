package com.example.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "venta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class venta {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "venta_seq"
    )
    @SequenceGenerator(
            name = "venta_seq",
            sequenceName = "seq_venta_id",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "numero_ticket", nullable = false, unique = true, length = 50)
    private String numeroTicket;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @Column(nullable = false, length = 30)
    private String estado;

    @Column(name = "base_imponible", nullable = false, precision = 10, scale = 2)
    private BigDecimal baseImponible = BigDecimal.ZERO;

    @Column(name = "total_igic", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalIgic = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tpv_id", nullable = false)
    private tpv tpv;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "turno_caja_id", nullable = false)
    private turnoCaja turnoCaja;

    @PrePersist
    public void prePersist() {
        if (fecha == null) {
            fecha = LocalDateTime.now();
        }
    }
}
