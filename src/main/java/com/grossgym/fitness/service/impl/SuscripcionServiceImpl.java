package com.grossgym.fitness.service.impl;

import com.grossgym.fitness.model.Suscripcion;
import com.grossgym.fitness.model.Socio;
import com.grossgym.fitness.model.Estado;
import com.grossgym.fitness.repository.SuscripcionRepository;
import com.grossgym.fitness.service.SuscripcionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de Suscripcion
 * Contiene la lógica de negocio para la gestión de suscripciones
 */
@Slf4j
@Service
@Transactional
public class SuscripcionServiceImpl implements SuscripcionService {

    private final SuscripcionRepository suscripcionRepository;

    public SuscripcionServiceImpl(SuscripcionRepository suscripcionRepository) {
        this.suscripcionRepository = suscripcionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Suscripcion> findAll() {
        log.debug("Obteniendo lista de todas las suscripciones");
        List<Suscripcion> suscripciones = suscripcionRepository.findAll();
        log.info("Se encontraron {} suscripciones", suscripciones.size());
        return suscripciones;
    }

    @SuppressWarnings("null")
	@Override
    @Transactional(readOnly = true)
    public Optional<Suscripcion> findById(Integer id) {
        log.debug("Buscando suscripción con ID: {}", id);
        Optional<Suscripcion> suscripcion = suscripcionRepository.findById(id);
        if (suscripcion.isPresent()) {
            log.debug("Suscripción encontrada con ID: {}", id);
        } else {
            log.warn("Suscripción no encontrada con ID: {}", id);
        }
        return suscripcion;
    }

    @Override
    public Suscripcion save(Suscripcion suscripcion) {
        log.info("Guardando suscripción para socio: {}", suscripcion.getSocio() != null ? suscripcion.getSocio().getRut() : "null");
        
        // Validaciones de negocio
        if (suscripcion.getSocio() == null) {
            log.error("Intento de guardar suscripción sin socio");
            throw new IllegalArgumentException("La suscripción debe tener un socio asociado");
        }
        if (suscripcion.getPlan() == null) {
            log.error("Intento de guardar suscripción sin plan");
            throw new IllegalArgumentException("La suscripción debe tener un plan asociado");
        }
        if (suscripcion.getTipoPago() == null) {
            log.error("Intento de guardar suscripción sin tipo de pago");
            throw new IllegalArgumentException("La suscripción debe tener un tipo de pago");
        }
        
        // Si es nueva suscripción, establecer fecha de creación
        if (suscripcion.getIdSuscripcion() == null) {
            suscripcion.setFechaCreacion(LocalDateTime.now());
            log.debug("Nueva suscripción - Fecha de creación establecida: {}", suscripcion.getFechaCreacion());
            
            // Calcular fecha de término según el plan
            if (suscripcion.getPlan().getDuracion() != null) {
                LocalDateTime fechaTermino = calcularFechaTermino(
                    suscripcion.getFechaCreacion(),
                    suscripcion.getPlan().getDuracion(),
                    suscripcion.getPlan().getUnidad()
                );
                suscripcion.setFechaTermino(fechaTermino);
                log.debug("Fecha de término calculada: {}", fechaTermino);
            }
        }
        
        try {
            Suscripcion suscripcionGuardada = suscripcionRepository.save(suscripcion);
            log.info("Suscripción guardada exitosamente con ID: {}", suscripcionGuardada.getIdSuscripcion());
            return suscripcionGuardada;
        } catch (Exception e) {
            log.error("Error al guardar suscripción", e);
            throw e;
        }
    }

    @SuppressWarnings("null")
	@Override
    public void deleteById(Integer id) {
        log.info("Eliminando suscripción con ID: {}", id);
        try {
            suscripcionRepository.deleteById(id);
            log.info("Suscripción eliminada exitosamente: {}", id);
        } catch (Exception e) {
            log.error("Error al eliminar suscripción con ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Suscripcion> findBySocio(Socio socio) {
        log.debug("Buscando suscripciones del socio: {}", socio.getRut());
        List<Suscripcion> suscripciones = suscripcionRepository.findBySocio(socio);
        log.debug("Se encontraron {} suscripciones para el socio {}", suscripciones.size(), socio.getRut());
        return suscripciones;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Suscripcion> findByEstado(Estado estado) {
        log.debug("Buscando suscripciones con estado: {}", estado.getDescripcion());
        List<Suscripcion> suscripciones = suscripcionRepository.findByEstado(estado);
        log.debug("Se encontraron {} suscripciones con estado '{}'", suscripciones.size(), estado.getDescripcion());
        return suscripciones;
    }

    @Override
    @Transactional(readOnly = true)
    public Suscripcion findByNroTransaccion(String nroTransaccion) {
        log.debug("Buscando suscripción por número de transacción: {}", nroTransaccion);
        Suscripcion suscripcion = suscripcionRepository.findByNroTransaccion(nroTransaccion);
        if (suscripcion != null) {
            log.debug("Suscripción encontrada con nro. transacción: {}", nroTransaccion);
        }
        return suscripcion;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Suscripcion> findSuscripcionesVigentes() {
        log.debug("Buscando suscripciones vigentes (fecha > ahora)");
        List<Suscripcion> suscripciones = suscripcionRepository.findSuscripcionesVigentes(LocalDateTime.now());
        log.info("Se encontraron {} suscripciones vigentes", suscripciones.size());
        return suscripciones;
    }
    
    /**
     * Calcula la fecha de término de una suscripción
     */
    private LocalDateTime calcularFechaTermino(LocalDateTime fechaInicio, Integer duracion, String unidad) {
        return switch (unidad.toUpperCase()) {
            case "DIA" -> fechaInicio.plusDays(duracion);
            case "MES" -> fechaInicio.plusMonths(duracion);
            case "AÑO", "ANIO" -> fechaInicio.plusYears(duracion);
            default -> fechaInicio.plusMonths(duracion);
        };
    }
}

