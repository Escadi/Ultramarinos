package com.example.backend.Service.Service;

import com.example.backend.Model.Cliente;
import com.example.backend.Model.RolCliente;
import com.example.backend.Repository.ClienteJPA;
import com.example.backend.Repository.RolClienteJPA;
import com.example.backend.Service.IMP.ClienteServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClienteService implements ClienteServiceImpl {

    private final ClienteJPA clienteJPA;
    private final RolClienteJPA rolClienteJPA;
    private static final Logger logger =
            LoggerFactory.getLogger(ClienteService.class);


    @Override
    public List<Cliente> ListadoCliente() {
        return clienteJPA.findAll();
    }

    @Override
    public Cliente addCliente(Cliente cliente) {
        return clienteJPA.save(cliente);
    }

    @Override
    public Cliente updateCliente(Long id, Cliente cliente) {
        try {
            if (!id.equals(cliente.getId())) {
                logger.warn("El ID {} no coincide con el ID del cliente", id);
                throw new IllegalArgumentException("Los ID no coinciden");
            }

            Cliente clienteActualizado = clienteJPA.save(cliente);

            logger.info("Cliente actualizado correctamente con ID: {}", id);

            return clienteActualizado;

        } catch (Exception e) {
            logger.error("Error al actualizar el cliente con ID: {}", id, e);
            throw e;
        }
    }


    @Override
    public void deleteCliente(Long id) {
        if(!clienteJPA.existsById(id)) {
            throw new IllegalArgumentException("El ID del cliente no existe");
        }
        clienteJPA.deleteById(id);

    }

    @Override
    public Optional<Cliente> findcodigocliente(String codigoCliente) {
        return null;
    }

    @Override
    public List<RolCliente> ListadoRolCliente() {
        return rolClienteJPA.findAll();
    }


}
