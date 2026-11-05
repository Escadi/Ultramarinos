package com.example.backend.DTO.Mappers;

import com.example.backend.DTO.PagoDTO;
import com.example.backend.Model.Pago;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = VentaMapper.class)
public interface PagoMapper {
    Pago toEntity(PagoDTO dto);
    PagoDTO toDto(Pago pago);
    List<PagoDTO> toDtoList(List<Pago> list);
    List<Pago> toEntityList(List<PagoDTO> list);
}

