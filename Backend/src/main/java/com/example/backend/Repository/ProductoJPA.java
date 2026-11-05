package com.example.backend.Repository;

import com.example.backend.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoJPA extends JpaRepository<Producto, Long> {
    Optional<Producto> findByCodigoProducto(String codigoProducto);
    List<Producto> findByCategoriaNombreCategoria(String nombreCategoria);
}
