package com.example.backend.Service.Service;

import com.example.backend.Model.DetallePedidoProveedor;
import com.example.backend.Repository.DetallePedidoProveedorJPA;
import com.example.backend.Service.IMP.DetallePedidoProveedorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DetallePedidoProveedorService implements DetallePedidoProveedorServiceImpl {

    private final DetallePedidoProveedorJPA detallePedidoProveedorJPA;

    @Override
    public List<DetallePedidoProveedor> getAll() {
        return detallePedidoProveedorJPA.findAll();
    }

    @Override
    public Optional<DetallePedidoProveedor> getById(Long id) {
        return detallePedidoProveedorJPA.findById(id);
    }

    @Override
    public List<DetallePedidoProveedor> getByPedidoProveedorId(Long pedidoId) {
        return detallePedidoProveedorJPA.findByPedidoProveedorId(pedidoId);
    }

    @Override
    public DetallePedidoProveedor save(DetallePedidoProveedor detalle) {
        return detallePedidoProveedorJPA.save(detalle);
    }

    @Override
    public DetallePedidoProveedor update(Long id, DetallePedidoProveedor detalle) {
        DetallePedidoProveedor existing = detallePedidoProveedorJPA.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "DetallePedidoProveedor no encontrado con ID: " + id));
        existing.setCantidad(detalle.getCantidad());
        existing.setPrecioUnitario(detalle.getPrecioUnitario());
        existing.setDescuento(detalle.getDescuento());
        existing.setSubtotal(detalle.getSubtotal());
        existing.setPedidoProveedor(detalle.getPedidoProveedor());
        existing.setProductoProveedor(detalle.getProductoProveedor());
        return detallePedidoProveedorJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!detallePedidoProveedorJPA.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DetallePedidoProveedor no encontrado con ID: " + id);
        }
        detallePedidoProveedorJPA.deleteById(id);
    }
}

