package com.example.backend.DTO;

import com.example.backend.Model.ProductoProveedor;
import lombok.*;

import java.math.BigDecimal;

/**
 * DTO de la entidad {@link ProductoProveedor}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductoProveedorDTO {

    private Long id;
    private BigDecimal precioCompra;
    private Boolean preferente;

    private Long productoId;
    private String productoNombre;

    private Long proveedorId;
    private String proveedorNombre;

}