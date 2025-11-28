package com.grossgym.fitness.controller;

import com.grossgym.fitness.model.Estado;
import com.grossgym.fitness.service.EstadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de Estados
 */
@RestController
@RequestMapping("/estados")
@Tag(name = "Estados", description = "API para gestión de estados de suscripciones")
public class EstadoController {

    private final EstadoService estadoService;

    public EstadoController(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    @GetMapping
    public ResponseEntity<List<Estado>> getAllEstados() {
        return ResponseEntity.ok(estadoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> getEstadoById(@PathVariable Integer id) {
        return estadoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Estado> createEstado(@RequestBody Estado estado) {
        Estado nuevoEstado = estadoService.save(estado);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEstado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estado> updateEstado(@PathVariable Integer id, @RequestBody Estado estado) {
        return estadoService.findById(id)
                .map(e -> {
                    estado.setIdEstado(id);
                    return ResponseEntity.ok(estadoService.save(estado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstado(@PathVariable Integer id) {
        return estadoService.findById(id)
                .map(e -> {
                    estadoService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar estados habilitados", 
               description = "Obtiene solo los estados habilitados (Activo, Vencido, Suspendido, etc.)")
    @GetMapping("/habilitados")
    public ResponseEntity<List<Estado>> getEstadosHabilitados() {
        return ResponseEntity.ok(estadoService.findByHabilitado(true));
    }
}

