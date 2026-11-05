package com.example.backend.DTO.Mappers;

import com.example.backend.DTO.MovimientoStockDTO;
import com.example.backend.Model.MovimientoStock;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductoMapper.class, EmpleadoMapper.class})
public interface MovimientoStockMapper {
    MovimientoStock toEntity(MovimientoStockDTO dto);
    MovimientoStockDTO toDto(MovimientoStock movimientoStock);
    List<MovimientoStockDTO> toDtoList(List<MovimientoStock> list);
    List<MovimientoStock> toEntityList(List<MovimientoStockDTO> list);
}

