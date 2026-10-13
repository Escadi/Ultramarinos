package com.example.backend.Model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "turno_caja")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TurnoCaja {


    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "turno_caja_seq"
    )
    @SequenceGenerator(
            name = "turno_caja_seq",
            sequenceName = "seq_turno_caja_id",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "fondo_caja", nullable = false, precision = 10, scale = 2)
    private BigDecimal fondoCaja = BigDecimal.ZERO;

    @Column(name = "estado_caja", nullable = false, length = 30)
    private String estadoCaja;

    @Column(name = "saldo_real", precision = 10, scale = 2)
    private BigDecimal saldoReal;

    @Column(name = "saldo_esperado", precision = 10, scale = 2)
    private BigDecimal saldoEsperado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "empleado_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_turno_empleado")
    )
    private Empleado empleado;
}
