package com.example.backend.DTO;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoriaDTO {

    private Long id;
    private String nombreCategoria;
    private String descripcion;

}
