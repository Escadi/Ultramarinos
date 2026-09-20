package com.example.backend.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "factura")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class factura {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "factura_seq"
    )
    @SequenceGenerator(
            name = "factura_seq",
            sequenceName = "seq_factura_id",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "numero_factura", nullable = false, unique = true, length = 50)
    private String numeroFactura;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private cliente cliente;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "venta_id", nullable = false, unique = true)
    private venta venta;

    @PrePersist
    public void prePersist() {
        if (fecha == null) {
            fecha = LocalDateTime.now();
        }
    }
}
