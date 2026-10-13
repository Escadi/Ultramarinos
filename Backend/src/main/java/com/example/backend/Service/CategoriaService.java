package com.example.backend.Service;

import com.example.backend.DTO.*;

import java.util.List;

public interface CategoriaService {

    List<CategoriaDTO> listar();

    CategoriaDTO obtener(Long id);

    CategoriaDTO crear(CategoriaDTO dto);

    CategoriaDTO actualizar(Long id, CategoriaDTO dto);

    void eliminar(Long id);
}
