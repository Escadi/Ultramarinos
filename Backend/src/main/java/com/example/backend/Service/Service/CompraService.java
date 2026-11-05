package com.example.backend.Service.Service;

import com.example.backend.Model.Compra;
import com.example.backend.Repository.CompraJPA;
import com.example.backend.Service.IMP.CompraServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class CompraService implements CompraServiceImpl {

    private final CompraJPA compraJPA;

    @Override
    public List<Compra> getAll() {
        return compraJPA.findAll();
    }

    @Override
    public Optional<Compra> getById(Long id) {
        return compraJPA.findById(id);
    }

    @Override
    public Compra save(Compra compra) {
        return compraJPA.save(compra);
    }

    @Override
    public Compra update(Long id, Compra compra) {
        Compra existing = compraJPA.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra no encontrada con ID: " + id));
        existing.setNumeroCompra(compra.getNumeroCompra());
        existing.setFecha(compra.getFecha());
        existing.setTotal(compra.getTotal());
        existing.setEstado(compra.getEstado());
        existing.setPedidoProveedor(compra.getPedidoProveedor());
        existing.setEmpleado(compra.getEmpleado());
        return compraJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!compraJPA.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra no encontrada con ID: " + id);
        }
        compraJPA.deleteById(id);
    }
}

