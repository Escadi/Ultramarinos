package com.example.backend.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class categoria {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "categoria_seq"
    )
    @SequenceGenerator(
            name = "categoria_seq",
            sequenceName = "seq_categoria_id",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "nombre_categoria", nullable = false, unique = true, length = 100)
    private String nombreCategoria;

    @Column(length = 255)
    private String descripcion;

}
