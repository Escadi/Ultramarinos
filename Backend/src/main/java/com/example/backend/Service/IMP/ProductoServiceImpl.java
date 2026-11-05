package com.example.backend.Service.IMP;

import com.example.backend.Model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoServiceImpl {

    List<Producto> getAllProductos();
    Optional<Producto> getProductoById(Long id);
    Producto getProductoByCodigoProducto(String codigoProducto);
    Producto addProducto(Producto producto);
    Producto updateProducto(Long id, Producto producto);
    void deleteProducto(Producto producto);
    void deleteProductoById(Long id);
    List<Producto> getProductosByCategoria(String categoria);
}
