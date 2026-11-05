package com.example.backend.Service.Service;

import com.example.backend.Model.ProductoProveedor;
import com.example.backend.Repository.ProductoProveedorJPA;
import com.example.backend.Service.IMP.ProductoProveedorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoProveedorService implements ProductoProveedorServiceImpl {

    private final ProductoProveedorJPA productoProveedorJPA;

    @Override
    public List<ProductoProveedor> getAll() {
        return productoProveedorJPA.findAll();
    }

    @Override
    public Optional<ProductoProveedor> getById(Long id) {
        return productoProveedorJPA.findById(id);
    }

    @Override
    public List<ProductoProveedor> getByProductoId(Long productoId) {
        return productoProveedorJPA.findByProductoId(productoId);
    }

    @Override
    public List<ProductoProveedor> getByProveedorId(Long proveedorId) {
        return productoProveedorJPA.findByProveedorId(proveedorId);
    }

    @Override
    public ProductoProveedor save(ProductoProveedor productoProveedor) {
        return productoProveedorJPA.save(productoProveedor);
    }

    @Override
    public ProductoProveedor update(Long id, ProductoProveedor productoProveedor) {
        ProductoProveedor existing = productoProveedorJPA.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ProductoProveedor no encontrado con ID: " + id));
        existing.setPrecioCompra(productoProveedor.getPrecioCompra());
        existing.setPreferente(productoProveedor.getPreferente());
        existing.setProducto(productoProveedor.getProducto());
        existing.setProveedor(productoProveedor.getProveedor());
        return productoProveedorJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!productoProveedorJPA.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ProductoProveedor no encontrado con ID: " + id);
        }
        productoProveedorJPA.deleteById(id);
    }
}

