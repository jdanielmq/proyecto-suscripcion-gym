package com.grossgym.fitness.service;

import com.grossgym.fitness.model.Estado;
import java.util.List;
import java.util.Optional;

public interface EstadoService {
    List<Estado> findAll();
    Optional<Estado> findById(Integer id);
    Estado save(Estado estado);
    void deleteById(Integer id);
    List<Estado> findByHabilitado(Boolean habilitado);
}

