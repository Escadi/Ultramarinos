package com.example.backend.Repository;

import com.example.backend.Model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaJPA extends JpaRepository<Categoria, Long> {
}
