package com.example.backend.DTO;

import lombok.*;

/**
 * DTO de la entidad {@link RolCliente}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RolClienteDTO {

    private Long id;
    private String tipoCliente;

}
