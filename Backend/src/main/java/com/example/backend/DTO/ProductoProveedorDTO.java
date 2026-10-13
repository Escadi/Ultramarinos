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
public class ProductoProveedorDTO {

    private Long id;
    private BigDecimal precioCompra;
    private Boolean preferente;

    private Long productoId;
    private String productoNombre;

    private Long proveedorId;
    private String proveedorNombre;

    /**
     * Convierte una entidad {@link ProductoProveedor} en su DTO.
     */
    public static ProductoProveedorDTO fromEntity(ProductoProveedor entity) {
        if (entity == null) {
            return null;
        }
        return ProductoProveedorDTO.builder()
                .id(entity.getId())
                .precioCompra(entity.getPrecioCompra())
                .preferente(entity.getPreferente())
                .productoId(entity.getProducto() != null ? entity.getProducto().getId() : null)
                .productoNombre(entity.getProducto() != null ? entity.getProducto().getNombre() : null)
                .proveedorId(entity.getProveedor() != null ? entity.getProveedor().getId() : null)
                .proveedorNombre(entity.getProveedor() != null ? entity.getProveedor().getNombreProveedor() : null)
                .build();
    }

    /**
     * Convierte el DTO en una entidad {@link ProductoProveedor} (solo campos simples).
     * Las relaciones (producto, proveedor) deben asignarse en la capa de servicio.
     */
    public ProductoProveedor toEntity() {
        ProductoProveedor entity = new ProductoProveedor();
        entity.setId(this.id);
        if (this.precioCompra != null) entity.setPrecioCompra(this.precioCompra);
        if (this.preferente != null) entity.setPreferente(this.preferente);
        return entity;
    }
}