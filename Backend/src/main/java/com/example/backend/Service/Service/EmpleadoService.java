package com.example.backend.Service.Service;

import com.example.backend.Model.Empleado;
import com.example.backend.Model.RolTrabajador;
import com.example.backend.Repository.JPA.EmpleadoJPA;
import com.example.backend.Repository.JPA.RolTrabajadorJPA;
import com.example.backend.Service.IMP.EmpleadoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpleadoService implements EmpleadoServiceImpl {

    private final EmpleadoJPA empleadoJPA;
    private final RolTrabajadorJPA rolTrabajadorJPA;

    @Override
    @Transactional(readOnly = true)
    public List<Empleado> listado() {
        return empleadoJPA.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Empleado> findById(Long id) {
        return empleadoJPA.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Empleado> findByCodigoEmpleado(String codigoEmpleado) {
        return empleadoJPA.findByCodigoEmpleado(codigoEmpleado);
    }

    @Override
    @Transactional
    public Empleado agregarEmpleado(Empleado empleado) {
        return empleadoJPA.save(empleado);
    }

    @Override
    @Transactional
    public Optional<Empleado> actualizar(Long id, Empleado empleadoActualizado) {
        return empleadoJPA.findById(id).map(existente -> {
            if (empleadoActualizado.getCodigoEmpleado() != null) {
                existente.setCodigoEmpleado(empleadoActualizado.getCodigoEmpleado());
            }
            if (empleadoActualizado.getNombre() != null) {
                existente.setNombre(empleadoActualizado.getNombre());
            }
            if (empleadoActualizado.getApellido() != null) {
                existente.setApellido(empleadoActualizado.getApellido());
            }
            if (empleadoActualizado.getCorreoElectronico() != null) {
                existente.setCorreoElectronico(empleadoActualizado.getCorreoElectronico());
            }
            if (empleadoActualizado.getContrasenia() != null && !empleadoActualizado.getContrasenia().isBlank()) {
                existente.setContrasenia(empleadoActualizado.getContrasenia());
            }
            if (empleadoActualizado.getActivo() != null) {
                existente.setActivo(empleadoActualizado.getActivo());
            }
            if (empleadoActualizado.getRolTrabajador() != null) {
                existente.setRolTrabajador(empleadoActualizado.getRolTrabajador());
            }
            return empleadoJPA.save(existente);
        });
    }

    @Override
    @Transactional
    public boolean eliminar(Long id) {
        if (empleadoJPA.existsById(id)) {
            empleadoJPA.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolTrabajador> Rollist() {
        return  rolTrabajadorJPA.findAll();
    }
}
