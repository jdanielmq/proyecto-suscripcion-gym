package com.grossgym.fitness.controller;

import com.grossgym.fitness.model.Suscripcion;
import com.grossgym.fitness.service.SuscripcionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de Suscripciones
 * Expone endpoints para operaciones CRUD de suscripciones
 */
@Slf4j
@RestController
@RequestMapping("/suscripciones")
@Tag(name = "Suscripciones", description = "API para gestión de suscripciones de socios")
public class SuscripcionController {

    private final SuscripcionService suscripcionService;

    public SuscripcionController(SuscripcionService suscripcionService) {
        this.suscripcionService = suscripcionService;
    }

    /**
     * Obtiene todas las suscripciones
     * GET /api/suscripciones
     */
    @Operation(summary = "Listar todas las suscripciones", 
               description = "Obtiene una lista completa de todas las suscripciones con sus relaciones")
    @GetMapping
    public ResponseEntity<List<Suscripcion>> getAllSuscripciones() {
        List<Suscripcion> suscripciones = suscripcionService.findAll();
        return ResponseEntity.ok(suscripciones);
    }

    /**
     * Obtiene una suscripción por ID
     * GET /api/suscripciones/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Suscripcion> getSuscripcionById(@PathVariable Integer id) {
        return suscripcionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea una nueva suscripción
     * POST /api/suscripciones
     */
    @Operation(summary = "Crear nueva suscripción", 
               description = "Registra una nueva suscripción. Calcula automáticamente la fecha de término según el plan.")
    @PostMapping
    public ResponseEntity<Suscripcion> createSuscripcion(@RequestBody Suscripcion suscripcion) {
        try {
            log.info("=== CREAR SUSCRIPCIÓN ===");
            log.debug("Suscripción recibida: {}", suscripcion);
            log.debug("Socio RUT: {}", suscripcion.getSocio() != null ? suscripcion.getSocio().getRut() : "null");
            log.debug("Plan ID: {}", suscripcion.getPlan() != null ? suscripcion.getPlan().getIdPlan() : "null");
            log.debug("Tipo Pago ID: {}", suscripcion.getTipoPago() != null ? suscripcion.getTipoPago().getIdPago() : "null");
            log.debug("Monto Plan: {}, Monto Matrícula: {}", suscripcion.getMontoPlan(), suscripcion.getMontoMatricula());
            
            Suscripcion nuevaSuscripcion = suscripcionService.save(suscripcion);
            log.info("Suscripción creada exitosamente con ID: {}", nuevaSuscripcion.getIdSuscripcion());
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSuscripcion);
        } catch (IllegalArgumentException e) {
            log.error("Error de validación al crear suscripción: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error inesperado al crear suscripción", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Actualiza una suscripción existente
     * PUT /api/suscripciones/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Suscripcion> updateSuscripcion(
            @PathVariable Integer id,
            @RequestBody Suscripcion suscripcion) {
        return suscripcionService.findById(id)
                .map(suscripcionExistente -> {
                    suscripcion.setIdSuscripcion(id);
                    Suscripcion suscripcionActualizada = suscripcionService.save(suscripcion);
                    return ResponseEntity.ok(suscripcionActualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina una suscripción por ID
     * DELETE /api/suscripciones/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSuscripcion(@PathVariable Integer id) {
        return suscripcionService.findById(id)
                .map(suscripcion -> {
                    suscripcionService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene suscripciones vigentes
     * GET /api/suscripciones/vigentes
     */
    @Operation(summary = "Listar suscripciones vigentes", 
               description = "Obtiene todas las suscripciones cuya fecha de término es posterior a la fecha actual")
    @GetMapping("/vigentes")
    public ResponseEntity<List<Suscripcion>> getSuscripcionesVigentes() {
        List<Suscripcion> suscripciones = suscripcionService.findSuscripcionesVigentes();
        return ResponseEntity.ok(suscripciones);
    }

    /**
     * Busca una suscripción por número de transacción
     * GET /api/suscripciones/transaccion/{nroTransaccion}
     */
    @GetMapping("/transaccion/{nroTransaccion}")
    public ResponseEntity<Suscripcion> getSuscripcionByNroTransaccion(@PathVariable String nroTransaccion) {
        Suscripcion suscripcion = suscripcionService.findByNroTransaccion(nroTransaccion);
        if (suscripcion != null) {
            return ResponseEntity.ok(suscripcion);
        }
        return ResponseEntity.notFound().build();
    }
}

