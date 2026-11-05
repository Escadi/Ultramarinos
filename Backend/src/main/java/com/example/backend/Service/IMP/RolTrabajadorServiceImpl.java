package com.example.backend.Service.IMP;

import com.example.backend.Model.RolTrabajador;

import java.util.List;
import java.util.Optional;

public interface RolTrabajadorServiceImpl {
    List<RolTrabajador> getAll();
    Optional<RolTrabajador> getById(Long id);
    RolTrabajador save(RolTrabajador rolTrabajador);
    RolTrabajador update(Long id, RolTrabajador rolTrabajador);
    void delete(Long id);
}

