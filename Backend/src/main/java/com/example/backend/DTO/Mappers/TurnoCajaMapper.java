package com.example.backend.DTO.Mappers;

import com.example.backend.DTO.TurnoCajaDTO;
import com.example.backend.Model.TurnoCaja;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = EmpleadoMapper.class)
public interface TurnoCajaMapper {
    TurnoCaja toEntity(TurnoCajaDTO dto);
    TurnoCajaDTO toDto(TurnoCaja turnoCaja);
    List<TurnoCajaDTO> toDtoList(List<TurnoCaja> list);
    List<TurnoCaja> toEntityList(List<TurnoCajaDTO> list);
}

