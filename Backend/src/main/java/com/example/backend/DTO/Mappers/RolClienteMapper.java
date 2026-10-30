package com.example.backend.DTO.Mappers;


import com.example.backend.DTO.ClienteDTO;
import com.example.backend.DTO.RolClienteDTO;
import com.example.backend.Model.RolCliente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RolClienteMapper {


    RolCliente toEntity(RolClienteDTO RolClienteDTO);
    RolClienteDTO toDto(RolCliente rolCliente);

    List<RolClienteDTO> toDtoList(List<RolCliente> rolClientes);
    List<RolCliente> toEntityList(List<RolClienteDTO> rolClienteDTOS);
}
