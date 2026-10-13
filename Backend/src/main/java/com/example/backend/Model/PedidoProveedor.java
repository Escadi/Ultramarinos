package com.example.backend.Model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedido_proveedor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoProveedor {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "pedido_proveedor_seq"
    )
    @SequenceGenerator(
            name = "pedido_proveedor_seq",
            sequenceName = "seq_pedido_proveedor_id",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "numero_pedido", nullable = false, unique = true, length = 50)
    private String numeroPedido;

    @Column(name = "fecha_pedido", nullable = false)
    private LocalDateTime fechaPedido;

    @Column(nullable = false, length = 30)
    private String estado;

    @Column(name = "total_estimado", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalEstimado = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    @PrePersist
    public void prePersist() {
        if (fechaPedido == null) {
            fechaPedido = LocalDateTime.now();
        }
    }

}
