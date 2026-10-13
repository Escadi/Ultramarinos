package com.example.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "rolcliente",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_rol_cliente",
                        columnNames = "tipocliente"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class rolCliente {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rol_cliente_seq"
    )
    @SequenceGenerator(
            name = "rol_cliente_seq",
            sequenceName = "seq_rolcliente_id",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "tipocliente", nullable = false, length = 50)
    private String tipoCliente;
}
