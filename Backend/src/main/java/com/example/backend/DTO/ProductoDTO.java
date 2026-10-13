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

    /**
     * Convierte una entidad {@link Producto} en su DTO.
     */
    public static ProductoDTO fromEntity(Producto entity) {
        if (entity == null) {
            return null;
        }
        return ProductoDTO.builder()
                .id(entity.getId())
                .codigoProducto(entity.getCodigoProducto())
                .nombre(entity.getNombre())
                .precioCompra(entity.getPrecioCompra())
                .precioVenta(entity.getPrecioVenta())
                .stockActual(entity.getStockActual())
                .stockMinimo(entity.getStockMinimo())
                .activo(entity.getActivo())
                .descripcion(entity.getDescripcion())
                .categoriaId(entity.getCategoria() != null ? entity.getCategoria().getId() : null)
                .categoriaNombre(entity.getCategoria() != null ? entity.getCategoria().getNombreCategoria() : null)
                .build();
    }

    /**
     * Convierte el DTO en una entidad {@link Producto} (solo campos simples).
     * Las relaciones (categoria) deben asignarse en la capa de servicio.
     */
    public Producto toEntity() {
        Producto entity = new Producto();
        entity.setId(this.id);
        if (this.codigoProducto != null) entity.setCodigoProducto(this.codigoProducto);
        if (this.nombre != null) entity.setNombre(this.nombre);
        if (this.precioCompra != null) entity.setPrecioCompra(this.precioCompra);
        if (this.precioVenta != null) entity.setPrecioVenta(this.precioVenta);
        if (this.stockActual != null) entity.setStockActual(this.stockActual);
        if (this.stockMinimo != null) entity.setStockMinimo(this.stockMinimo);
        if (this.activo != null) entity.setActivo(this.activo);
        if (this.descripcion != null) entity.setDescripcion(this.descripcion);
        return entity;
    }
}