package com.example.backend.Repository;

import com.example.backend.Model.MovimientoStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoStockJPA extends JpaRepository<MovimientoStock, Long> {
    List<MovimientoStock> findByProductoId(Long productoId);
}

