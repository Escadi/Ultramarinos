package com.example.backend.Repository;

import com.example.backend.Model.RolTrabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolClienteJPA extends JpaRepository<RolTrabajador, Long> {
}
