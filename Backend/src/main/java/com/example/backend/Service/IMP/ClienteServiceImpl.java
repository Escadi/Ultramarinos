package com.example.backend.Service.IMP;

import com.example.backend.DTO.ClienteDTO;
import com.example.backend.Model.Cliente;
import com.example.backend.Model.RolCliente;

import java.util.List;
import java.util.Optional;

public interface ClienteServiceImpl {

    /**
     * CLIENT
     * */

    List<Cliente> ListadoCliente();
    Cliente addCliente(Cliente clienteDTO);
    Cliente updateCliente(Long id,Cliente clienteDTO);
    void deleteCliente(Long id);
    Optional<Cliente> findcodigocliente (String codigoCliente);
    List<RolCliente>  ListadoRolCliente();

}
