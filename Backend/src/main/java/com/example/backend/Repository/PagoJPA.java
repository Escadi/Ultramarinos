package com.example.backend.Repository;

import com.example.backend.Model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoJPA extends JpaRepository<Pago, Long> {
    List<Pago> findByVentaId(Long ventaId);
}

