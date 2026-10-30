package com.example.backend.DTO.Mappers;

import com.example.backend.DTO.ClienteDTO;
import com.example.backend.Model.Cliente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {


    Cliente toEntity(ClienteDTO clienteDTO);
    ClienteDTO toDto(Cliente cliente);

    List<ClienteDTO> toDto(List<Cliente> clientes);
    List<Cliente> toEntityList(List<ClienteDTO> clienteDTO);
}
