package com.example.backend.DTO.Mappers;

import com.example.backend.DTO.DetalleCompraDTO;
import com.example.backend.Model.DetalleCompra;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CompraMapper.class, ProductoMapper.class})
public interface DetalleCompraMapper {
    DetalleCompra toEntity(DetalleCompraDTO dto);
    DetalleCompraDTO toDto(DetalleCompra detalleCompra);
    List<DetalleCompraDTO> toDtoList(List<DetalleCompra> list);
    List<DetalleCompra> toEntityList(List<DetalleCompraDTO> list);
}

