package com.example.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class cliente {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cliente_seq"
    )
    @SequenceGenerator(
            name = "cliente_seq",
            sequenceName = "seq_cliente_id",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 150)
    private String apellido;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "correo_electronico", length = 150)
    private String correoElectronico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "rolcliente_id",
            foreignKey = @ForeignKey(name = "fk_cliente_rol")
    )
    private rolCliente rolCliente;

    @PrePersist
    public void prePersist() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
    }




}
