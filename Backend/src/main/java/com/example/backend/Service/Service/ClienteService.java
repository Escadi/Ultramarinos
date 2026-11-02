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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
            if (id == null) {
                logger.warn("El ID {} no coincide con el ID del cliente", id);
                throw new IllegalArgumentException("Los ID no coinciden");
            }

            Cliente update = clienteJPA.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Cliente no encontrado con ID: " + id
                    ));
            update.setNombre(cliente.getNombre());
            update.setApellido(cliente.getApellido());
            update.setId(id);
            update.setRolCliente(cliente.getRolCliente());
            update.setCorreoElectronico(cliente.getCorreoElectronico());

            logger.info("Cliente actualizado correctamente con ID: {}", id);
            return clienteJPA.save(update);


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
