package com.example.backend.DTO.Mappers;

import com.example.backend.DTO.PedidoProveedorDTO;
import com.example.backend.Model.PedidoProveedor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = EmpleadoMapper.class)
public interface PedidoProveedorMapper {
    PedidoProveedor toEntity(PedidoProveedorDTO dto);
    PedidoProveedorDTO toDto(PedidoProveedor pedidoProveedor);
    List<PedidoProveedorDTO> toDtoList(List<PedidoProveedor> list);
    List<PedidoProveedor> toEntityList(List<PedidoProveedorDTO> list);
}

