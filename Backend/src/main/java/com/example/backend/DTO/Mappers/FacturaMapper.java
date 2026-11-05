package com.example.backend.DTO.Mappers;

import com.example.backend.DTO.FacturaDTO;
import com.example.backend.Model.Factura;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class, VentaMapper.class})
public interface FacturaMapper {
    Factura toEntity(FacturaDTO dto);
    FacturaDTO toDto(Factura factura);
    List<FacturaDTO> toDtoList(List<Factura> list);
    List<Factura> toEntityList(List<FacturaDTO> list);
}

