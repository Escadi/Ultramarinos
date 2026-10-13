package com.example.backend.Service;

import com.example.backend.DTO.*;

import java.util.List;

public interface ClienteService {

    List<ClienteDTO> listar();

    ClienteDTO obtener(Long id);

    ClienteDTO crear(ClienteDTO dto);

    ClienteDTO actualizar(Long id, ClienteDTO dto);

    void eliminar(Long id);

    List<ClienteDTO> buscarPorNombre(String nombre);

    List<ClienteDTO> listarPorRol(Long rolClienteId);
}
