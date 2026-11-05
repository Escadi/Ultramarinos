package com.example.backend.DTO.Mappers;

import com.example.backend.DTO.ProductoProveedorDTO;
import com.example.backend.Model.ProductoProveedor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductoMapper.class, ProveedorMapper.class})
public interface ProductoProveedorMapper {
    ProductoProveedor toEntity(ProductoProveedorDTO dto);
    ProductoProveedorDTO toDto(ProductoProveedor productoProveedor);
    List<ProductoProveedorDTO> toDtoList(List<ProductoProveedor> list);
    List<ProductoProveedor> toEntityList(List<ProductoProveedorDTO> list);
}

