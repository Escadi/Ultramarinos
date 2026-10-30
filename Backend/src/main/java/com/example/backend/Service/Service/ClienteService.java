package com.example.backend.Service.Service;

import com.example.backend.DTO.ClienteDTO;
import com.example.backend.DTO.Mappers.ClienteMapper;
import com.example.backend.DTO.Mappers.RolClienteMapper;
import com.example.backend.Model.Cliente;
import com.example.backend.Repository.ClienteJPA;
import com.example.backend.Repository.RolClienteJPA;
import com.example.backend.Repository.RolTrabajadorJPA;
import com.example.backend.Service.IMP.ClienteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService implements ClienteServiceImpl {

    private final ClienteJPA clienteJPA;
    private final RolClienteJPA rolClienteJPA;


    @Override
    @Transactional(readOnly = true)
    public List<Cliente> ListadoCliente() {
        return clienteJPA.findAll();
    }

    @Override
    @Transactional
    public Cliente addCliente(Cliente cliente) {
        return clienteJPA.save(cliente);
    }

    @Override
    public Cliente updateCliente(Long id, Cliente cliente) {
        if(id.equals(cliente.getId())){
            return clienteJPA.save(cliente);
        }

        return clienteJPA.save(cliente);
    }

    @Override
    public void deleteCliente(Long id) {

    }
}
