package com.example.backend.DTO.Mappers;

import com.example.backend.DTO.DetallePedidoProveedorDTO;
import com.example.backend.Model.DetallePedidoProveedor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PedidoProveedorMapper.class, ProductoProveedorMapper.class})
public interface DetallePedidoProveedorMapper {
    DetallePedidoProveedor toEntity(DetallePedidoProveedorDTO dto);
    DetallePedidoProveedorDTO toDto(DetallePedidoProveedor detalle);
    List<DetallePedidoProveedorDTO> toDtoList(List<DetallePedidoProveedor> list);
    List<DetallePedidoProveedor> toEntityList(List<DetallePedidoProveedorDTO> list);
}

