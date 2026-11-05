package com.example.backend.Service.Service;

import com.example.backend.Model.DetalleCompra;
import com.example.backend.Repository.DetalleCompraJPA;
import com.example.backend.Service.IMP.DetalleCompraServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DetalleCompraService implements DetalleCompraServiceImpl {

    private final DetalleCompraJPA detalleCompraJPA;

    @Override
    public List<DetalleCompra> getAll() {
        return detalleCompraJPA.findAll();
    }

    @Override
    public Optional<DetalleCompra> getById(Long id) {
        return detalleCompraJPA.findById(id);
    }

    @Override
    public List<DetalleCompra> getByCompraId(Long compraId) {
        return detalleCompraJPA.findByCompraId(compraId);
    }

    @Override
    public DetalleCompra save(DetalleCompra detalleCompra) {
        return detalleCompraJPA.save(detalleCompra);
    }

    @Override
    public DetalleCompra update(Long id, DetalleCompra detalleCompra) {
        DetalleCompra existing = detalleCompraJPA.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "DetalleCompra no encontrado con ID: " + id));
        existing.setCantidad(detalleCompra.getCantidad());
        existing.setPrecioUnitario(detalleCompra.getPrecioUnitario());
        existing.setTipoIgic(detalleCompra.getTipoIgic());
        existing.setDescuento(detalleCompra.getDescuento());
        existing.setSubtotal(detalleCompra.getSubtotal());
        existing.setCompra(detalleCompra.getCompra());
        existing.setProducto(detalleCompra.getProducto());
        return detalleCompraJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!detalleCompraJPA.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DetalleCompra no encontrado con ID: " + id);
        }
        detalleCompraJPA.deleteById(id);
    }
}

