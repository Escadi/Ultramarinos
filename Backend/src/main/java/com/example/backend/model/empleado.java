package com.example.backend.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "empleado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class empleado {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "empleado_seq"
    )
    @SequenceGenerator(
            name = "empleado_seq",
            sequenceName = "seq_empleado_id",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "codigo_empleado", nullable = false, unique = true, length = 50)
    private String codigoEmpleado;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 150)
    private String apellido;

    @Column(name = "correo_electronico", unique = true, length = 150)
    private String correoElectronico;

    @Column(nullable = false, length = 255)
    private String contrasenia;

    @Column(nullable = false)
    private Boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "rol_trabajador_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_empleado_rol")
    )
    private rolTrabajador rolTrabajador;
}
