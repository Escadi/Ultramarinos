package com.example.backend.Service.IMP;

import com.example.backend.DTO.ClienteDTO;
import com.example.backend.Model.Cliente;

import java.util.List;

public interface ClienteServiceImpl {

    /**
     * CLIENT
     * */

    List<Cliente> ListadoCliente();
    Cliente addCliente(Cliente clienteDTO);
    Cliente updateCliente(Long id,Cliente clienteDTO);
    void deleteCliente(Long id);
}
