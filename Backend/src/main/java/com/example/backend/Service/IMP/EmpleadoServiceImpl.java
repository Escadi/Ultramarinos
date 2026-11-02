package com.example.backend.Service.IMP;

import com.example.backend.Model.Empleado;
import com.example.backend.Model.RolTrabajador;

import java.util.List;
import java.util.Optional;

public interface EmpleadoServiceImpl {


    /**
     * WORKER
     * */
    List<Empleado> listado();

    Optional<Empleado> findByCodigoEmpleado(String codigoEmpleado);

    Empleado agregarEmpleado(Empleado empleado);

    Empleado actualizar(Long id, Empleado empleado);

    boolean eliminar(Long id);

    /**
     * ROL FOR THE WORKER
     * */
    List<RolTrabajador> Rollist();
}
