package com.example.backend.Repository.IMP;

import com.example.backend.Model.Empleado;
import com.example.backend.Repository.Interface.EmpleadoInterface;
import com.example.backend.Repository.JPA.EmpleadoJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementacion de {@link EmpleadoInterface} delegando en {@link EmpleadoJPA}.
 */
@Repository
@RequiredArgsConstructor
public class EmpleadoImpl implements EmpleadoInterface {

    private final EmpleadoJPA empleadoJPA;

    @Override
    public List<Empleado> findAll() {
        return empleadoJPA.findAll();
    }

    @Override
    public Optional<Empleado> findById(Long id) {
        return empleadoJPA.findById(id);
    }

    @Override
    public Empleado save(Empleado empleado) {
        return empleadoJPA.save(empleado);
    }

    @Override
    public void deleteById(Long id) {
        empleadoJPA.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return empleadoJPA.existsById(id);
    }

    @Override
    public long count() {
        return empleadoJPA.count();
    }

    @Override
    public Optional<Empleado> findByCodigoEmpleado(String codigoEmpleado) {
        return empleadoJPA.findByCodigoEmpleado(codigoEmpleado);
    }

    @Override
    public Optional<Empleado> findByCorreoElectronico(String correoElectronico) {
        return empleadoJPA.findByCorreoElectronico(correoElectronico);
    }

    @Override
    public List<Empleado> findByActivoTrue() {
        return empleadoJPA.findByActivoTrue();
    }

    @Override
    public List<Empleado> findByRolTrabajadorId(Long rolTrabajadorId) {
        return empleadoJPA.findByRolTrabajadorId(rolTrabajadorId);
    }

    @Override
    public boolean existsByCodigoEmpleado(String codigoEmpleado) {
        return empleadoJPA.existsByCodigoEmpleado(codigoEmpleado);
    }
}