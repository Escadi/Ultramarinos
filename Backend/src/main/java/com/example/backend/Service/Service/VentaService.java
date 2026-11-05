package com.example.backend.Service.Service;

import com.example.backend.Model.Venta;
import com.example.backend.Repository.VentaJPA;
import com.example.backend.Service.IMP.VentaServiceImpl;
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
public class VentaService implements VentaServiceImpl {

    private final VentaJPA ventaJPA;

    @Override
    public List<Venta> getAll() {
        return ventaJPA.findAll();
    }

    @Override
    public Optional<Venta> getById(Long id) {
        return ventaJPA.findById(id);
    }

    @Override
    public Venta save(Venta venta) {
        return ventaJPA.save(venta);
    }

    @Override
    public Venta update(Long id, Venta venta) {
        Venta existing = ventaJPA.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venta no encontrada con ID: " + id));
        existing.setNumeroTicket(venta.getNumeroTicket());
        existing.setFecha(venta.getFecha());
        existing.setTotal(venta.getTotal());
        existing.setEstado(venta.getEstado());
        existing.setBaseImponible(venta.getBaseImponible());
        existing.setTotalIgic(venta.getTotalIgic());
        existing.setCliente(venta.getCliente());
        existing.setTpv(venta.getTpv());
        existing.setTurnoCaja(venta.getTurnoCaja());
        return ventaJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!ventaJPA.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venta no encontrada con ID: " + id);
        }
        ventaJPA.deleteById(id);
    }
}

