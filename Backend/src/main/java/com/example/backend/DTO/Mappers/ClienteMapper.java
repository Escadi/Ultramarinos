package com.example.backend.DTO.Mappers;

import com.example.backend.DTO.ClienteDTO;
import com.example.backend.Model.Cliente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = RolClienteMapper.class)
public interface ClienteMapper {

    Cliente toEntity(ClienteDTO clienteDTO);
    ClienteDTO toDto(Cliente cliente);

    List<ClienteDTO> toDtoList(List<Cliente> clientes);
    List<Cliente> toEntityList(List<ClienteDTO> clienteDTO);
}
