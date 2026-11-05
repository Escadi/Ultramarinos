package com.example.backend.Service.Service;

import com.example.backend.Model.PedidoProveedor;
import com.example.backend.Repository.PedidoProveedorJPA;
import com.example.backend.Service.IMP.PedidoProveedorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoProveedorService implements PedidoProveedorServiceImpl {

    private final PedidoProveedorJPA pedidoProveedorJPA;

    @Override
    public List<PedidoProveedor> getAll() {
        return pedidoProveedorJPA.findAll();
    }

    @Override
    public Optional<PedidoProveedor> getById(Long id) {
        return pedidoProveedorJPA.findById(id);
    }

    @Override
    public PedidoProveedor save(PedidoProveedor pedidoProveedor) {
        return pedidoProveedorJPA.save(pedidoProveedor);
    }

    @Override
    public PedidoProveedor update(Long id, PedidoProveedor pedidoProveedor) {
        PedidoProveedor existing = pedidoProveedorJPA.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PedidoProveedor no encontrado con ID: " + id));
        existing.setNumeroPedido(pedidoProveedor.getNumeroPedido());
        existing.setFechaPedido(pedidoProveedor.getFechaPedido());
        existing.setEstado(pedidoProveedor.getEstado());
        existing.setTotalEstimado(pedidoProveedor.getTotalEstimado());
        existing.setEmpleado(pedidoProveedor.getEmpleado());
        return pedidoProveedorJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!pedidoProveedorJPA.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PedidoProveedor no encontrado con ID: " + id);
        }
        pedidoProveedorJPA.deleteById(id);
    }
}

