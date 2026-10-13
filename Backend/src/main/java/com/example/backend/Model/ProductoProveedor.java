package com.example.backend.model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "producto_proveedor",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_producto_proveedor",
                        columnNames = {"producto_id", "proveedor_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class productoProveedor {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "producto_proveedor_seq"
    )
    @SequenceGenerator(
            name = "producto_proveedor_seq",
            sequenceName = "seq_producto_proveedor_id",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "precio_compra", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioCompra;

    @Column(nullable = false)
    private Boolean preferente = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private producto producto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proveedor_id", nullable = false)
    private proveedor proveedor;
}
