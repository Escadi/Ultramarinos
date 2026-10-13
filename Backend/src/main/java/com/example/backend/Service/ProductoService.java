package com.example.backend.Service;

import com.example.backend.DTO.*;

import java.util.List;

public interface ProductoService {

    List<ProductoDTO> listar();

    ProductoDTO obtener(Long id);

    ProductoDTO crear(ProductoDTO dto);

    ProductoDTO actualizar(Long id, ProductoDTO dto);

    void eliminar(Long id);

    List<ProductoDTO> buscarPorNombre(String nombre);

    List<ProductoDTO> listarPorCategoria(Long categoriaId);

    List<ProductoDTO> listarActivos();

    List<ProductoDTO> listarStockBajo();
}
