package com.example.backend.DTO.Mappers;


import com.example.backend.DTO.ProductoDTO;
import com.example.backend.Model.Producto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductoMapper {


    ProductoDTO toDTO(Producto producto);
    Producto toEntity(ProductoDTO productoDTO);
    List<ProductoDTO> toDTOList(List<Producto> productos);
    List<Producto> toEntityList(List<ProductoDTO> productoDTOS);
}
