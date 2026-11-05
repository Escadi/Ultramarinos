package com.example.backend.DTO.Mappers;

import com.example.backend.DTO.DetalleVentaDTO;
import com.example.backend.Model.DetalleVenta;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {VentaMapper.class, ProductoMapper.class})
public interface DetalleVentaMapper {
    DetalleVenta toEntity(DetalleVentaDTO dto);
    DetalleVentaDTO toDto(DetalleVenta detalleVenta);
    List<DetalleVentaDTO> toDtoList(List<DetalleVenta> list);
    List<DetalleVenta> toEntityList(List<DetalleVentaDTO> list);
}

