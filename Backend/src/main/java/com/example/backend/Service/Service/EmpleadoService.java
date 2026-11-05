package com.example.backend.Service.Service;

import com.example.backend.Model.Empleado;
import com.example.backend.Model.RolTrabajador;
import com.example.backend.Repository.EmpleadoJPA;
import com.example.backend.Repository.RolTrabajadorJPA;
import com.example.backend.Service.IMP.EmpleadoServiceImpl;
import com.example.backend.Untils.PasswordHash;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpleadoService implements EmpleadoServiceImpl {

    private final EmpleadoJPA empleadoJPA;
    private final RolTrabajadorJPA rolTrabajadorJPA;
    private static final Logger logger =
            LoggerFactory.getLogger(EmpleadoService.class);

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
    public Empleado findByCodigoEmpleado(String codigoEmpleado) {
        return empleadoJPA.findByCodigoEmpleado(codigoEmpleado);
    }

    @Override
    @Transactional
    public Empleado agregarEmpleado(Empleado empleado) {

        String password = empleado.getContrasenia();
        if(password == null || password.isEmpty()){
            throw new RuntimeException("Tiene que insertar la contraseña");
        }

        String hash = PasswordHash.passwordHash(password);
        empleado.setContrasenia(hash);

        return empleadoJPA.save(empleado);
    }

    @Override
    @Transactional
    public Empleado actualizar(Long id, Empleado empleadoActualizado) {

            Empleado update =  empleadoJPA.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Cliente no encontrado con ID: " + id
                    ));
            update.setCodigoEmpleado(empleadoActualizado.getCodigoEmpleado());
            update.setNombre(empleadoActualizado.getNombre());
            update.setApellido(empleadoActualizado.getApellido());
            update.setCorreoElectronico(empleadoActualizado.getCorreoElectronico());
            update.setActivo(empleadoActualizado.getActivo());
            return empleadoJPA.save(update);
    }

    @Override
    public boolean login(String codigoempleado, String password) throws IllegalAccessException {
        Empleado empleado = empleadoJPA.findByCodigoEmpleado(codigoempleado);
            if(empleado.getCodigoEmpleado() == null){
                throw new IllegalAccessException("El empleado no dispone de codigo de empleado");
            }
        return PasswordHash.checkPasswordHash(password, empleado.getContrasenia());
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
