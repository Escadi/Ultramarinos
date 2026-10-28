package com.example.backend.DTO.Mappers;

import com.example.backend.DTO.CategoriaDTO;
import com.example.backend.Model.Categoria;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    CategoriaDTO toDto(Categoria categoria);
    Categoria toEntity(CategoriaDTO categoriaDTO);

    List<Categoria>toEntityList(List<CategoriaDTO> categoriaDTOS);
    List<CategoriaDTO> toDtoList(List<Categoria> categorias);
}
