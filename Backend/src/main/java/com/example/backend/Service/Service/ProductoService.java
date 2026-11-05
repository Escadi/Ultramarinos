package com.example.backend.Service.Service;

import com.example.backend.Model.Producto;
import com.example.backend.Repository.ProductoJPA;
import com.example.backend.Service.IMP.ProductoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService implements ProductoServiceImpl {

    private final ProductoJPA productoJPA;

    @Override
    public List<Producto> getAllProductos() {
        return productoJPA.findAll();
    }

    @Override
    public Optional<Producto> getProductoById(Long id) {
        return productoJPA.findById(id);
    }

    @Override
    public Producto getProductoByCodigoProducto(String codigoProducto) {
        return productoJPA.findByCodigoProducto(codigoProducto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con código: " + codigoProducto));
    }

    @Override
    public Producto addProducto(Producto producto) {
        return productoJPA.save(producto);
    }

    @Override
    public Producto updateProducto(Long id, Producto producto) {
        Producto existing = productoJPA.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con ID: " + id));
        existing.setCodigoProducto(producto.getCodigoProducto());
        existing.setNombre(producto.getNombre());
        existing.setPrecioCompra(producto.getPrecioCompra());
        existing.setPrecioVenta(producto.getPrecioVenta());
        existing.setStockActual(producto.getStockActual());
        existing.setStockMinimo(producto.getStockMinimo());
        existing.setActivo(producto.getActivo());
        existing.setDescripcion(producto.getDescripcion());
        existing.setCategoria(producto.getCategoria());
        return productoJPA.save(existing);
    }

    @Override
    public void deleteProducto(Producto producto) {
        if (producto != null && producto.getId() != null) {
            deleteProductoById(producto.getId());
        }
    }

    @Override
    public void deleteProductoById(Long id) {
        if (!productoJPA.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con ID: " + id);
        }
        productoJPA.deleteById(id);
    }

    @Override
    public List<Producto> getProductosByCategoria(String categoria) {
        return productoJPA.findByCategoriaNombreCategoria(categoria);
    }
}
