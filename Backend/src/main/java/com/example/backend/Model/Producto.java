package com.example.backend.model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class producto {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "producto_seq"
    )
    @SequenceGenerator(
            name = "producto_seq",
            sequenceName = "seq_producto_id",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "codigo_producto", nullable = false, unique = true, length = 50)
    private String codigoProducto;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(name = "precio_compra", precision = 10, scale = 2)
    private BigDecimal precioCompra;

    @Column(name = "precio_venta", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioVenta;

    @Column(name = "stock_actual", nullable = false)
    private Integer stockActual = 0;

    @Column(name = "stock_minimo", nullable = false)
    private Integer stockMinimo = 0;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(length = 255)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "categoria_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_producto_categoria")
    )
    private categoria categoria;
}
