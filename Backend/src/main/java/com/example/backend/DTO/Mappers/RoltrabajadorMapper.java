package com.example.backend.DTO.Mappers;


import com.example.backend.DTO.RolTrabajadorDTO;
import com.example.backend.Model.RolTrabajador;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoltrabajadorMapper {

    RolTrabajadorDTO toDTO (RolTrabajador rolTrabajador);
    RolTrabajador toEntity (RolTrabajadorDTO rolTrabajadorDTO);

    List<RolTrabajadorDTO> toDto(List<RolTrabajador> rolTrabajador);
    List<RolTrabajador> toEntity(List<RolTrabajadorDTO> rolTrabajadorDTOS);


}
