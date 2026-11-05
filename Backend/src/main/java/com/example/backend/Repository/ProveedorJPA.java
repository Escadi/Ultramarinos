package com.example.backend.Repository;

import com.example.backend.Model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorJPA extends JpaRepository<Proveedor, Long> {
}

