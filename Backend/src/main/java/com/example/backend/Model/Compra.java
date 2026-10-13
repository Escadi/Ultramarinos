package com.example.backend.model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "compra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class compra {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "compra_seq"
    )
    @SequenceGenerator(
            name = "compra_seq",
            sequenceName = "seq_compra_id",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "numero_compra", unique = true, length = 50)
    private String numeroCompra;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @Column(length = 30)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_proveedor_id")
    private pedidoProveedor pedidoProveedor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empleado_id", nullable = false)
    private empleado empleado;

    @PrePersist
    public void prePersist() {
        if (fecha == null) {
            fecha = LocalDateTime.now();
        }
    }
}
