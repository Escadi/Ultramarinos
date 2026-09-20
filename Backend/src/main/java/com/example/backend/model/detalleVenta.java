package com.example.backend.model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_venta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class detalleVenta {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "detalle_venta_seq"
    )
    @SequenceGenerator(
            name = "detalle_venta_seq",
            sequenceName = "seq_detalle_venta_id",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "tipo_igic", nullable = false, precision = 5, scale = 2)
    private BigDecimal tipoIgic = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal descuento = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "venta_id", nullable = false)
    private venta venta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private producto producto;
}
