package com.example.backend.Repository;

import com.example.backend.Model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio Spring Data JPA para {@link Empleado}.
 */
@Repository
public interface EmpleadoJPA extends JpaRepository<Empleado, Long> {


    Optional<Empleado> findByCodigoEmpleado(String codigoEmpleado);


}