package com.example.backend.Model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "producto_seq")
        @SequenceGenerator(name = "producto_seq", sequenceName = "seq_producto_id", allocationSize = 1)
        private Long id;

        @Column(name = "codigoProducto", nullable = false, unique = true, length = 50)
        private String codigoProducto;

        @Column(nullable = false, length = 150)
        private String nombre;

        @Column(name = "precioCompra", precision = 10, scale = 2)
        private BigDecimal precioCompra;

        @Column(name = "precioVenta", nullable = false, precision = 10, scale = 2)
        private BigDecimal precioVenta;

        @Column(name = "stockActual", nullable = false)
        private Integer stockActual = 0;

        @Column(name = "stockMinimo", nullable = false)
        private Integer stockMinimo = 0;

        @Column(nullable = false)
        private Boolean activo = true;

        @Column(length = 255)
        private String descripcion;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "categoria_id", nullable = false, foreignKey = @ForeignKey(name = "fk_producto_categoria"))
        private Categoria categoria;
}
