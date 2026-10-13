package com.example.backend.Repository.Interface;

import com.example.backend.Model.Empleado;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio de {@link Empleado}.
 */
public interface EmpleadoInterface {

    List<Empleado> findAll();

    Optional<Empleado> findById(Long id);

    Empleado save(Empleado empleado);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();

    Optional<Empleado> findByCodigoEmpleado(String codigoEmpleado);

    Optional<Empleado> findByCorreoElectronico(String correoElectronico);

    List<Empleado> findByActivoTrue();

    List<Empleado> findByRolTrabajadorId(Long rolTrabajadorId);

    boolean existsByCodigoEmpleado(String codigoEmpleado);
}