package com.example.backend.Service.Service;

import com.example.backend.Model.MovimientoStock;
import com.example.backend.Repository.MovimientoStockJPA;
import com.example.backend.Service.IMP.MovimientoStockServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovimientoStockService implements MovimientoStockServiceImpl {

    private final MovimientoStockJPA movimientoStockJPA;

    @Override
    public List<MovimientoStock> getAll() {
        return movimientoStockJPA.findAll();
    }

    @Override
    public Optional<MovimientoStock> getById(Long id) {
        return movimientoStockJPA.findById(id);
    }

    @Override
    public List<MovimientoStock> getByProductoId(Long productoId) {
        return movimientoStockJPA.findByProductoId(productoId);
    }

    @Override
    public MovimientoStock save(MovimientoStock movimientoStock) {
        return movimientoStockJPA.save(movimientoStock);
    }

    @Override
    public void delete(Long id) {
        if (!movimientoStockJPA.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "MovimientoStock no encontrado con ID: " + id);
        }
        movimientoStockJPA.deleteById(id);
    }
}

