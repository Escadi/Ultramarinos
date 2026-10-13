package com.example.backend.Model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_pedido_proveedor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedidoProveedor {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "detalle_pedido_proveedor_seq"
    )
    @SequenceGenerator(
            name = "detalle_pedido_proveedor_seq",
            sequenceName = "seq_detalle_pedido_proveedor_id",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal descuento = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pedido_proveedor_id", nullable = false)
    private PedidoProveedor pedidoProveedor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_proveedor_id", nullable = false)
    private ProductoProveedor productoProveedor;
}
