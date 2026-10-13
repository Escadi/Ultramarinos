package com.example.backend.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rol_trabajador")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolTrabajador {


    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rol_trabajador_seq"
    )
    @SequenceGenerator(
            name = "rol_trabajador_seq",
            sequenceName = "seq_roltrabajador_id",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "nombre_rol", nullable = false, unique = true, length = 50)
    private String nombreRol;


}
