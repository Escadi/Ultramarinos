package com.example.backend.DTO.Mappers;


import java.util.List;

import org.mapstruct.Mapper;

import com.example.backend.DTO.RolClienteDTO;
import com.example.backend.Model.RolCliente;

@Mapper(componentModel = "spring")
public interface RolClienteMapper {


    RolCliente toEntity(RolClienteDTO RolClienteDTO);
    RolClienteDTO toDto(RolCliente rolCliente);

    List<RolClienteDTO> toDtoList(List<RolCliente> rolCliente);
    List<RolCliente> toEntityList(List<RolClienteDTO> rolClienteDTO);
}
