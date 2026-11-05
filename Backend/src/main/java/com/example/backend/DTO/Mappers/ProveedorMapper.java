package com.example.backend.DTO.Mappers;

import com.example.backend.DTO.ProveedorDTO;
import com.example.backend.Model.Proveedor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProveedorMapper {
    Proveedor toEntity(ProveedorDTO dto);
    ProveedorDTO toDto(Proveedor proveedor);
    List<ProveedorDTO> toDtoList(List<Proveedor> list);
    List<Proveedor> toEntityList(List<ProveedorDTO> list);
}

