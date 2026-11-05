package com.example.backend.DTO.Mappers;

import com.example.backend.DTO.TelefonoClienteDTO;
import com.example.backend.Model.TelefonoCliente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ClienteMapper.class)
public interface TelefonoClienteMapper {
    TelefonoCliente toEntity(TelefonoClienteDTO dto);
    TelefonoClienteDTO toDto(TelefonoCliente telefonoCliente);
    List<TelefonoClienteDTO> toDtoList(List<TelefonoCliente> list);
    List<TelefonoCliente> toEntityList(List<TelefonoClienteDTO> list);
}

