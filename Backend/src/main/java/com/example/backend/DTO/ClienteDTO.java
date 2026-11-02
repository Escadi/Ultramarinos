package com.example.backend.DTO;

import com.example.backend.Model.Cliente;
import com.example.backend.Model.RolCliente;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClienteDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private LocalDateTime fechaCreacion;
    private String correoElectronico;
    private RolClienteDTO rolCliente;
}