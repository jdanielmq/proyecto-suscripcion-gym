package com.grossgym.fitness.service;

import com.grossgym.fitness.model.Socio;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz del servicio de Socio
 * Define las operaciones de negocio para la gestión de socios
 */
public interface SocioService {
    
    List<Socio> findAll();
    
    Optional<Socio> findById(String rut);
    
    Socio save(Socio socio);
    
    void deleteById(String rut);
    
    List<Socio> findByHabilitado(Boolean habilitado);
    
    Socio findByCorreo(String correo);
    
    List<Socio> findByNombresContaining(String nombres);
}

