package com.example.backend.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "proveedor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class proveedor {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "proveedor_seq"
    )
    @SequenceGenerator(
            name = "proveedor_seq",
            sequenceName = "seq_proveedor_id",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String cif;

    @Column(length = 150)
    private String contacto;

    @Column(name = "nombre_proveedor", nullable = false, length = 150)
    private String nombreProveedor;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(length = 150)
    private String email;

    @Column(length = 20)
    private String telefono;
}
