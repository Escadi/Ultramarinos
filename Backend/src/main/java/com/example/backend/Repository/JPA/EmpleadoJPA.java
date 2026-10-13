package com.example.backend.Repository.JPA;

import com.example.backend.Model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio Spring Data JPA para {@link Empleado}.
 */
public interface EmpleadoJPA extends JpaRepository<Empleado, Long> {

    Optional<Empleado> findByCodigoEmpleado(String codigoEmpleado);

    Optional<Empleado> findByCorreoElectronico(String correoElectronico);

    List<Empleado> findByActivoTrue();

    List<Empleado> findByRolTrabajadorId(Long rolTrabajadorId);

    boolean existsByCodigoEmpleado(String codigoEmpleado);
}