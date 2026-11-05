package com.example.backend.Service.Service;

import com.example.backend.Model.DetalleVenta;
import com.example.backend.Repository.DetalleVentaJPA;
import com.example.backend.Service.IMP.DetalleVentaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DetalleVentaService implements DetalleVentaServiceImpl {

    private final DetalleVentaJPA detalleVentaJPA;

    @Override
    public List<DetalleVenta> getAll() {
        return detalleVentaJPA.findAll();
    }

    @Override
    public Optional<DetalleVenta> getById(Long id) {
        return detalleVentaJPA.findById(id);
    }

    @Override
    public List<DetalleVenta> getByVentaId(Long ventaId) {
        return detalleVentaJPA.findByVentaId(ventaId);
    }

    @Override
    public DetalleVenta save(DetalleVenta detalleVenta) {
        return detalleVentaJPA.save(detalleVenta);
    }

    @Override
    public DetalleVenta update(Long id, DetalleVenta detalleVenta) {
        DetalleVenta existing = detalleVentaJPA.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "DetalleVenta no encontrado con ID: " + id));
        existing.setCantidad(detalleVenta.getCantidad());
        existing.setPrecioUnitario(detalleVenta.getPrecioUnitario());
        existing.setTipoIgic(detalleVenta.getTipoIgic());
        existing.setDescuento(detalleVenta.getDescuento());
        existing.setSubtotal(detalleVenta.getSubtotal());
        existing.setVenta(detalleVenta.getVenta());
        existing.setProducto(detalleVenta.getProducto());
        return detalleVentaJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!detalleVentaJPA.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DetalleVenta no encontrado con ID: " + id);
        }
        detalleVentaJPA.deleteById(id);
    }
}

