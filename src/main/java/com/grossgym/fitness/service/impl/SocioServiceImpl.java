package com.grossgym.fitness.service.impl;

import com.grossgym.fitness.model.Socio;
import com.grossgym.fitness.repository.SocioRepository;
import com.grossgym.fitness.service.SocioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de Socio
 * Contiene la lógica de negocio para la gestión de socios
 */
@Slf4j
@Service
@Transactional
public class SocioServiceImpl implements SocioService {

    private final SocioRepository socioRepository;

    public SocioServiceImpl(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Socio> findAll() {
        log.debug("Obteniendo lista de todos los socios");
        List<Socio> socios = socioRepository.findAll();
        log.info("Se encontraron {} socios", socios.size());
        return socios;
    }

    @SuppressWarnings("null")
	@Override
    @Transactional(readOnly = true)
    public Optional<Socio> findById(String rut) {
        log.debug("Buscando socio con RUT: {}", rut);
        Optional<Socio> socio = socioRepository.findById(rut);
        if (socio.isPresent()) {
            log.debug("Socio encontrado: {}", socio.get().getNombres());
        } else {
            log.warn("Socio no encontrado con RUT: {}", rut);
        }
        return socio;
    }

    @Override
    public Socio save(Socio socio) {
        log.info("Guardando socio con RUT: {}", socio.getRut());
        
        // Validaciones de negocio
        if (socio.getRut() == null || socio.getRut().trim().isEmpty()) {
            log.error("Intento de guardar socio sin RUT");
            throw new IllegalArgumentException("El RUT del socio no puede estar vacío");
        }
        if (socio.getCorreo() == null || !socio.getCorreo().contains("@")) {
            log.error("Intento de guardar socio con correo inválido: {}", socio.getCorreo());
            throw new IllegalArgumentException("El correo debe ser válido");
        }
        
        try {
            Socio socioGuardado = socioRepository.save(socio);
            log.info("Socio guardado exitosamente: {} - {}", socioGuardado.getRut(), socioGuardado.getNombres());
            return socioGuardado;
        } catch (Exception e) {
            log.error("Error al guardar socio con RUT: {}", socio.getRut(), e);
            throw e;
        }
    }

    @SuppressWarnings("null")
	@Override
    public void deleteById(String rut) {
        log.info("Eliminando socio con RUT: {}", rut);
        try {
            socioRepository.deleteById(rut);
            log.info("Socio eliminado exitosamente: {}", rut);
        } catch (Exception e) {
            log.error("Error al eliminar socio con RUT: {}", rut, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Socio> findByHabilitado(Boolean habilitado) {
        log.debug("Buscando socios con habilitado: {}", habilitado);
        List<Socio> socios = socioRepository.findByHabilitado(habilitado);
        log.debug("Se encontraron {} socios con habilitado={}", socios.size(), habilitado);
        return socios;
    }

    @Override
    @Transactional(readOnly = true)
    public Socio findByCorreo(String correo) {
        log.debug("Buscando socio por correo: {}", correo);
        Socio socio = socioRepository.findByCorreo(correo);
        if (socio != null) {
            log.debug("Socio encontrado con correo: {}", correo);
        }
        return socio;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Socio> findByNombresContaining(String nombres) {
        log.debug("Buscando socios que contengan: {}", nombres);
        List<Socio> socios = socioRepository.findByNombresContaining(nombres);
        log.debug("Se encontraron {} socios con nombres que contienen '{}'", socios.size(), nombres);
        return socios;
    }
}

