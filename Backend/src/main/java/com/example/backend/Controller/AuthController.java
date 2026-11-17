package com.example.backend.Controller;

import com.example.backend.DTO.LoginRequestDTO;
import com.example.backend.DTO.LoginResponseDTO;
import com.example.backend.Model.Empleado;
import com.example.backend.Repository.EmpleadoJPA;
import com.example.backend.Security.JwtService;
import com.example.backend.Untils.PasswordHash;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final EmpleadoJPA empleadoJPA;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        logger.info("Intento de login con JWT para codigoEmpleado: {}", request.getCodigoEmpleado());

        if (request.getCodigoEmpleado() == null || request.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debes proporcionar el código de empleado y la contraseña");
        }

        String codigo = request.getCodigoEmpleado().trim();
        Empleado empleado = empleadoJPA.findByCodigoEmpleado(codigo);

        if (empleado == null) {
            logger.warn("Login fallido: empleado no encontrado con código {}", codigo);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Código de empleado o contraseña incorrectos");
        }

        if (Boolean.FALSE.equals(empleado.getActivo())) {
            logger.warn("Login fallido: empleado inactivo {}", codigo);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "El empleado se encuentra inactivo");
        }

        // Validación de contraseña segura
        boolean passwordMatches = false;
        try {
            passwordMatches = passwordEncoder.matches(request.getPassword(), empleado.getContrasenia());
        } catch (Exception ignored) {}

        if (!passwordMatches) {
            try {
                passwordMatches = PasswordHash.checkPasswordHash(request.getPassword(), empleado.getContrasenia());
            } catch (Exception ignored) {}
        }

        if (!passwordMatches && request.getPassword().equals(empleado.getContrasenia())) {
            passwordMatches = true;
        }

        if (!passwordMatches) {
            logger.warn("Login fallido: contraseña incorrecta para código {}", codigo);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Código de empleado o contraseña incorrectos");
        }

        String rol = empleado.getRolTrabajador() != null ? empleado.getRolTrabajador().getNombreRol() : "EMPLEADO";

        // Claims del JWT
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", empleado.getId());
        extraClaims.put("codigoEmpleado", empleado.getCodigoEmpleado());
        extraClaims.put("nombre", empleado.getNombre());
        extraClaims.put("apellido", empleado.getApellido());
        extraClaims.put("correoElectronico", empleado.getCorreoElectronico());
        extraClaims.put("rol", rol);

        // Token firmado con 60 minutos de vigencia
        String jwtToken = jwtService.generateToken(empleado.getCodigoEmpleado(), extraClaims);

        logger.info("JWT generado exitosamente para empleado: {} ({}) (validez: 60 min)", empleado.getNombre(), codigo);

        LoginResponseDTO response = LoginResponseDTO.builder()
                .token(jwtToken)
                .codigoEmpleado(empleado.getCodigoEmpleado())
                .nombre(empleado.getNombre())
                .apellido(empleado.getApellido())
                .correoElectronico(empleado.getCorreoElectronico())
                .rol(rol)
                .build();

        return ResponseEntity.ok(response);
    }
}
