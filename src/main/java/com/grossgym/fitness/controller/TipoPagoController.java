package com.grossgym.fitness.controller;

import com.grossgym.fitness.model.TipoPago;
import com.grossgym.fitness.service.TipoPagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de Tipos de Pago
 */
@RestController
@RequestMapping("/tipos-pago")
@Tag(name = "Tipos de Pago", description = "API para gestión de métodos de pago")
public class TipoPagoController {

    private final TipoPagoService tipoPagoService;

    public TipoPagoController(TipoPagoService tipoPagoService) {
        this.tipoPagoService = tipoPagoService;
    }

    @GetMapping
    public ResponseEntity<List<TipoPago>> getAllTiposPago() {
        return ResponseEntity.ok(tipoPagoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoPago> getTipoPagoById(@PathVariable Integer id) {
        return tipoPagoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoPago> createTipoPago(@RequestBody TipoPago tipoPago) {
        TipoPago nuevoTipoPago = tipoPagoService.save(tipoPago);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTipoPago);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoPago> updateTipoPago(@PathVariable Integer id, @RequestBody TipoPago tipoPago) {
        return tipoPagoService.findById(id)
                .map(tp -> {
                    tipoPago.setIdPago(id);
                    return ResponseEntity.ok(tipoPagoService.save(tipoPago));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoPago(@PathVariable Integer id) {
        return tipoPagoService.findById(id)
                .map(tp -> {
                    tipoPagoService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar tipos de pago activos", 
               description = "Obtiene solo los tipos de pago habilitados (Efectivo, Débito, Crédito, etc.)")
    @GetMapping("/activos")
    public ResponseEntity<List<TipoPago>> getTiposPagoActivos() {
        return ResponseEntity.ok(tipoPagoService.findByEstado(true));
    }
}

