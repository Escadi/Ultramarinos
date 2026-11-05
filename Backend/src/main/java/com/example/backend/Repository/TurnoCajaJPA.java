package com.example.backend.Repository;

import com.example.backend.Model.TurnoCaja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoCajaJPA extends JpaRepository<TurnoCaja, Long> {
}

