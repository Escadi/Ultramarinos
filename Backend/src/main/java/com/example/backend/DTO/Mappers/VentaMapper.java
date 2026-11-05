package com.example.backend.DTO.Mappers;

import com.example.backend.DTO.VentaDTO;
import com.example.backend.Model.Venta;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class, TpvMapper.class, TurnoCajaMapper.class})
public interface VentaMapper {
    Venta toEntity(VentaDTO dto);
    VentaDTO toDto(Venta venta);
    List<VentaDTO> toDtoList(List<Venta> list);
    List<Venta> toEntityList(List<VentaDTO> list);
}

