package com.example.backend.DTO.Mappers;

import com.example.backend.DTO.ProductoDTO;
import com.example.backend.Model.Producto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CategoriaMapper.class)
public interface ProductoMapper {

    Producto toEntity(ProductoDTO dto);
    ProductoDTO toDto(Producto producto);
    List<ProductoDTO> toDtoList(List<Producto> list);
    List<Producto> toEntityList(List<ProductoDTO> list);
}
