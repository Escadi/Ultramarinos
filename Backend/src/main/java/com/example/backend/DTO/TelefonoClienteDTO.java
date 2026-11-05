package com.example.backend.DTO;

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
    private ClienteDTO cliente;

}
