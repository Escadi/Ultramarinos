package com.example.backend.DTO.Mappers;

import com.example.backend.DTO.EmpleadoDTO;
import com.example.backend.Model.Empleado;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmpleadoMapper {

    // DTO -> Entity
    Empleado toEntity(EmpleadoDTO empleadoDTO);

    // Entity -> DTO (la contrasenia no se expone hacia el cliente)
    @Mapping(target = "contrasenia", ignore = true)
    EmpleadoDTO toDto(Empleado empleado);

    // List<Entity> -> List<DTO>
    List<EmpleadoDTO> toDto(List<Empleado> empleados);

    List<EmpleadoDTO> toDtoList(List<Empleado> empleados);

    // List<DTO> -> List<Entity>
    List<Empleado> toEntity(List<EmpleadoDTO> dtos);

    List<Empleado> toEntityList(List<EmpleadoDTO> dtos);

    // Actualización de entidad existente desde DTO ignorando valores nulos
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(EmpleadoDTO empleadoDTO, @MappingTarget Empleado empleado);
}
