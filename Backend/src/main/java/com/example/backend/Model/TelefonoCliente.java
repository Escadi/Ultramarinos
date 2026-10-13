package com.example.backend.Model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "telefono_cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TelefonoCliente {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "telefono_cliente_seq"
    )
    @SequenceGenerator(
            name = "telefono_cliente_seq",
            sequenceName = "seq_telefono_cliente_id",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "telefono_cliente", nullable = false, length = 20)
    private String telefonoCliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "cliente_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_telefono_cliente")
    )
    private Cliente cliente;
}
