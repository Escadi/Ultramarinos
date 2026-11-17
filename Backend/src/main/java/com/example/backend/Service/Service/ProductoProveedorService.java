package com.example.backend.Service.Service;

import com.example.backend.Model.ProductoProveedor;
import com.example.backend.Repository.ProductoProveedorJPA;
import com.example.backend.Service.IMP.ProductoProveedorServiceImpl;
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
public class ProductoProveedorService implements ProductoProveedorServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(ProductoProveedorService.class);

    private final ProductoProveedorJPA productoProveedorJPA;

    @Override
    public List<ProductoProveedor> getAll() {
        logger.info("Obteniendo listado de todos los productos por proveedor");
        return productoProveedorJPA.findAll();
    }

    @Override
    public Optional<ProductoProveedor> getById(Long id) {
        logger.info("Buscando producto de proveedor por ID: {}", id);
        return productoProveedorJPA.findById(id);
    }

    @Override
    public List<ProductoProveedor> getByProductoId(Long productoId) {
        logger.info("Obteniendo proveedores para el producto con ID: {}", productoId);
        return productoProveedorJPA.findByProductoId(productoId);
    }

    @Override
    public List<ProductoProveedor> getByProveedorId(Long proveedorId) {
        logger.info("Obteniendo productos para el proveedor con ID: {}", proveedorId);
        return productoProveedorJPA.findByProveedorId(proveedorId);
    }

    @Override
    public ProductoProveedor save(ProductoProveedor productoProveedor) {
        logger.info("Guardando relación producto-proveedor");
        return productoProveedorJPA.save(productoProveedor);
    }

    @Override
    public ProductoProveedor update(Long id, ProductoProveedor productoProveedor) {
        logger.info("Actualizando relación producto-proveedor con ID: {}", id);
        ProductoProveedor existing = productoProveedorJPA.findById(id)
                .orElseThrow(() -> {
                    logger.warn("ProductoProveedor no encontrado con ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "ProductoProveedor no encontrado con ID: " + id);
                });
        existing.setPrecioCompra(productoProveedor.getPrecioCompra());
        existing.setPreferente(productoProveedor.getPreferente());
        existing.setProducto(productoProveedor.getProducto());
        existing.setProveedor(productoProveedor.getProveedor());
        return productoProveedorJPA.save(existing);
    }

    @Override
    public void delete(Long id) {
        logger.info("Eliminando relación producto-proveedor con ID: {}", id);
        if (!productoProveedorJPA.existsById(id)) {
            logger.warn("No se puede eliminar, ProductoProveedor no encontrado con ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ProductoProveedor no encontrado con ID: " + id);
        }
        productoProveedorJPA.deleteById(id);
    }
}
