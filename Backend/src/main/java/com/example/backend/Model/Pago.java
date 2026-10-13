package com.example.backend.Model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "pago_seq"
    )
    @SequenceGenerator(
            name = "pago_seq",
            sequenceName = "seq_pago_id",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, length = 50)
    private String metodo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal importe;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    @PrePersist
    public void prePersist() {
        if (fechaPago == null) {
            fechaPago = LocalDateTime.now();
        }
    }
}
