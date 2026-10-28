package com.example.backend.DTO;

import com.example.backend.Model.Producto;
import lombok.*;

import java.math.BigDecimal;

/**
 * DTO de la entidad {@link Producto}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductoDTO {

    private Long id;
    private String codigoProducto;
    private String nombre;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private Integer stockActual;
    private Integer stockMinimo;
    private Boolean activo;
    private String descripcion;

    private Long categoriaId;
    private String categoriaNombre;

}