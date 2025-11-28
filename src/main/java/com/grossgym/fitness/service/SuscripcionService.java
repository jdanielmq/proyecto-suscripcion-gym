package com.grossgym.fitness.service;

import com.grossgym.fitness.model.Suscripcion;
import com.grossgym.fitness.model.Socio;
import com.grossgym.fitness.model.Estado;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz del servicio de Suscripcion
 * Define las operaciones de negocio para la gestión de suscripciones
 */
public interface SuscripcionService {
    
    List<Suscripcion> findAll();
    
    Optional<Suscripcion> findById(Integer id);
    
    Suscripcion save(Suscripcion suscripcion);
    
    void deleteById(Integer id);
    
    List<Suscripcion> findBySocio(Socio socio);
    
    List<Suscripcion> findByEstado(Estado estado);
    
    Suscripcion findByNroTransaccion(String nroTransaccion);
    
    List<Suscripcion> findSuscripcionesVigentes();
}

