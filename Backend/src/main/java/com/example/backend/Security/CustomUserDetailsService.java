package com.example.backend.Security;

import com.example.backend.Model.Empleado;
import com.example.backend.Repository.EmpleadoJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final EmpleadoJPA empleadoJPA;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Empleado empleado = empleadoJPA.findByCorreoElectronico(username)
                .orElseGet(() -> {
                    Empleado emp = empleadoJPA.findByCodigoEmpleado(username);
                    if (emp == null) {
                        throw new UsernameNotFoundException("Empleado no encontrado con identificador: " + username);
                    }
                    return emp;
                });

        if (Boolean.FALSE.equals(empleado.getActivo())) {
            throw new UsernameNotFoundException("El empleado se encuentra inactivo");
        }

        String rolNombre = "USER";
        if (empleado.getRolTrabajador() != null && empleado.getRolTrabajador().getNombreRol() != null) {
            rolNombre = empleado.getRolTrabajador().getNombreRol().toUpperCase();
        }

        return new User(
                empleado.getCorreoElectronico() != null ? empleado.getCorreoElectronico() : empleado.getCodigoEmpleado(),
                empleado.getContrasenia(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rolNombre))
        );
    }
}

