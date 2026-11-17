package com.example.backend.Service.Service;

import com.example.backend.Model.Producto;
import com.example.backend.Repository.ProductoJPA;
import com.example.backend.Service.IMP.ProductoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService implements ProductoServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(ProductoService.class);

    private final ProductoJPA productoJPA;

    @Override
    public List<Producto> getAllProductos() {
        logger.info("Obteniendo listado de todos los productos");
        return productoJPA.findAll();
    }

    @Override
    public Optional<Producto> getProductoById(Long id) {
        logger.info("Buscando producto por ID: {}", id);
        return productoJPA.findById(id);
    }

    @Override
    public Producto getProductoByCodigoProducto(String codigoProducto) {
        logger.info("Buscando producto por codigo: {}", codigoProducto);
        return productoJPA.findByCodigoProducto(codigoProducto)
                .orElseThrow(() -> {
                    logger.warn("Producto no encontrado con codigo: {}", codigoProducto);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con código: " + codigoProducto);
                });
    }

    @Override
    public Producto addProducto(Producto producto) {
        logger.info("Agregando producto: {}", producto.getNombre());
        return productoJPA.save(producto);
    }

    @Override
    public Producto updateProducto(Long id, Producto producto) {
        logger.info("Actualizando producto con ID: {}", id);
        Producto existing = productoJPA.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Producto no encontrado con ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con ID: " + id);
                });
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
        logger.info("Eliminando producto con ID: {}", id);
        if (!productoJPA.existsById(id)) {
            logger.warn("No se puede eliminar, producto no encontrado con ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con ID: " + id);
        }
        productoJPA.deleteById(id);
    }

    @Override
    public List<Producto> getProductosByCategoria(String categoria) {
        logger.info("Obteniendo productos de la categoria: {}", categoria);
        return productoJPA.findByCategoriaNombreCategoria(categoria);
    }
}
