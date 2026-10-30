package com.example.backend.Repository;

import com.example.backend.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteJPA extends JpaRepository<Cliente, Long> {
}
