package com.example.backend.Service;

import com.example.backend.DTO.*;

import java.util.List;

public interface ProveedorService {

    List<ProveedorDTO> listar();

    ProveedorDTO obtener(Long id);

    ProveedorDTO crear(ProveedorDTO dto);

    ProveedorDTO actualizar(Long id, ProveedorDTO dto);

    void eliminar(Long id);

    List<ProveedorDTO> buscarPorNombre(String nombre);

    List<ProveedorDTO> listarActivos();
}
