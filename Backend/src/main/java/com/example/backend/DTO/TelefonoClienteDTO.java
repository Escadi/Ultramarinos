package com.example.backend.DTO;

import com.example.backend.Model.TelefonoCliente;
import lombok.*;

/**
 * DTO de la entidad {@link TelefonoCliente}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TelefonoClienteDTO {

    private Long id;
    private String telefonoCliente;

    private Long clienteId;

}