package com.example.backend.Service.Service;

import com.example.backend.Model.Factura;
import com.example.backend.Repository.FacturaJPA;
import com.example.backend.Service.IMP.FacturaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FacturaService implements FacturaServiceImpl {

    private final FacturaJPA facturaJPA;

    @Override
    public List<Factura> getAll() {
        return facturaJPA.findAll();
    }

    @Override
    public Optional<Factura> getById(Long id) {
        return facturaJPA.findById(id);
    }

    @Override
    public Factura save(Factura factura) {
        return facturaJPA.save(factura);
    }

    @Override
    public void delete(Long id) {
        if (!facturaJPA.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Factura no encontrada con ID: " + id);
        }
        facturaJPA.deleteById(id);
    }
}

