package com.example.backend.repository.Interface;

import com.example.backend.model.Categoria;
import com.example.backend.model.Producto;

import java.util.List;

public interface ProductoImpl {

    List<Categoria> getCategorias();
    List<Producto> getProductos();
}
