package com.example.backend.DTO.Mappers;

import com.example.backend.DTO.CompraDTO;
import com.example.backend.Model.Compra;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PedidoProveedorMapper.class, EmpleadoMapper.class})
public interface CompraMapper {
    Compra toEntity(CompraDTO dto);
    CompraDTO toDto(Compra compra);
    List<CompraDTO> toDtoList(List<Compra> list);
    List<Compra> toEntityList(List<CompraDTO> list);
}

