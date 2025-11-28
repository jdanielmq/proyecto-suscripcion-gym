package com.grossgym.fitness.service;

import com.grossgym.fitness.model.TipoPago;
import java.util.List;
import java.util.Optional;

public interface TipoPagoService {
    List<TipoPago> findAll();
    Optional<TipoPago> findById(Integer id);
    TipoPago save(TipoPago tipoPago);
    void deleteById(Integer id);
    List<TipoPago> findByEstado(Boolean estado);
}

