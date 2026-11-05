package com.example.backend.DTO.Mappers;

import com.example.backend.DTO.TpvDTO;
import com.example.backend.Model.Tpv;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TpvMapper {
    Tpv toEntity(TpvDTO dto);
    TpvDTO toDto(Tpv tpv);
    List<TpvDTO> toDtoList(List<Tpv> list);
    List<Tpv> toEntityList(List<TpvDTO> list);
}

