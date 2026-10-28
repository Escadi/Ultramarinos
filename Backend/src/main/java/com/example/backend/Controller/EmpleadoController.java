package com.example.backend.Controller;

import com.example.backend.DTO.EmpleadoDTO;
import com.example.backend.DTO.Mappers.EmpleadoMapper;
import com.example.backend.Model.Empleado;
import com.example.backend.Service.Service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleado")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService empleadoService;
    private final EmpleadoMapper empleadoMapper;

    // READ ALL: Obtener todos los empleados
    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> listarEmpleado() {
        List<Empleado> empleados = empleadoService.listado();
        return ResponseEntity.ok(empleadoMapper.toDto(empleados));
    }

    // READ BY ID: Obtener empleado por ID
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> obtenerPorId(@PathVariable Long id) {
        return empleadoService.findById(id)
                .map(empleadoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // READ BY CODIGO: Obtener empleado por código de empleado
    @GetMapping("/codigo/{codigoEmpleado}")
    public ResponseEntity<EmpleadoDTO> obtenerPorCodigo(@PathVariable String codigoEmpleado) {
        return empleadoService.findByCodigoEmpleado(codigoEmpleado)
                .map(empleadoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE: Registrar un nuevo empleado
    @PostMapping
    public ResponseEntity<EmpleadoDTO> agregarEmpleado(@RequestBody EmpleadoDTO empleadoDTO) {
        // Conversión en el Controller: DTO -> Entidad
        Empleado entidad = empleadoMapper.toEntity(empleadoDTO);
        Empleado guardado = empleadoService.agregarEmpleado(entidad);
        // Conversión en el Controller: Entidad -> DTO
        EmpleadoDTO respuestaDTO = empleadoMapper.toDto(guardado);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuestaDTO);
    }

    // UPDATE: Actualizar empleado existente por ID
    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> actualizarEmpleado(@PathVariable Long id, @RequestBody EmpleadoDTO empleadoDTO) {
        // Conversión en el Controller: DTO -> Entidad
        Empleado entidad = empleadoMapper.toEntity(empleadoDTO);
        return empleadoService.actualizar(id, entidad)
                .map(empleadoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Eliminar empleado por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        boolean eliminado = empleadoService.eliminar(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
