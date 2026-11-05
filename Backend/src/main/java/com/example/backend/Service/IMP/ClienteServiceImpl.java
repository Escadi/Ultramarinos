package com.example.backend.Service.IMP;

import com.example.backend.Model.Cliente;
import com.example.backend.Model.RolCliente;

import java.util.List;
import java.util.Optional;

public interface ClienteServiceImpl {
    List<Cliente> ListadoCliente();
    Cliente getClienteById(Long id);
    Cliente addCliente(Cliente cliente);
    Cliente updateCliente(Long id, Cliente cliente);
    void deleteCliente(Long id);
    Optional<Cliente> findcodigocliente(String codigoCliente);
    List<RolCliente> ListadoRolCliente();
}
