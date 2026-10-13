package com.example.backend.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tpv")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tpv {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tpv_seq"
    )
    @SequenceGenerator(
            name = "tpv_seq",
            sequenceName = "seq_tpv_id",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "nombre_terminal", nullable = false, length = 100)
    private String nombreTerminal;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(length = 150)
    private String ubicacion;
}
