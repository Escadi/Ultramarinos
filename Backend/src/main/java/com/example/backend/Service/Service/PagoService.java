package com.example.backend.Service.Service;

import com.example.backend.Model.Pago;
import com.example.backend.Repository.PagoJPA;
import com.example.backend.Service.IMP.PagoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PagoService implements PagoServiceImpl {

    private final PagoJPA pagoJPA;

    @Override
    public List<Pago> getAll() {
        return pagoJPA.findAll();
    }

    @Override
    public Optional<Pago> getById(Long id) {
        return pagoJPA.findById(id);
    }

    @Override
    public List<Pago> getByVentaId(Long ventaId) {
        return pagoJPA.findByVentaId(ventaId);
    }

    @Override
    public Pago save(Pago pago) {
        return pagoJPA.save(pago);
    }

    @Override
    public Pago update(Long id, Pago pago) {
        Pago existing = pagoJPA.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no encontrado con ID: " + id));
        existing.setMetodo(pago.getMetodo());
        existing.setImporte(pago.getImporte());
        existing.setFechaPago(pago.getFechaPago());
        existing.setVenta(pago.getVenta());
        return pagoJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!pagoJPA.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no encontrado con ID: " + id);
        }
        pagoJPA.deleteById(id);
    }
}

