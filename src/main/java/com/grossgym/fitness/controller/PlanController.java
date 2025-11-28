package com.grossgym.fitness.controller;

import com.grossgym.fitness.model.Plan;
import com.grossgym.fitness.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de Planes
 */
@RestController
@RequestMapping("/planes")
@Tag(name = "Planes", description = "API para gestión de planes de membresía")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @Operation(summary = "Listar todos los planes", 
               description = "Obtiene todos los planes de membresía disponibles (Diario, Mensual, Anual, etc.)")
    @GetMapping
    public ResponseEntity<List<Plan>> getAllPlanes() {
        return ResponseEntity.ok(planService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> getPlanById(@PathVariable Integer id) {
        return planService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Plan> createPlan(@RequestBody Plan plan) {
        Plan nuevoPlan = planService.save(plan);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPlan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plan> updatePlan(@PathVariable Integer id, @RequestBody Plan plan) {
        return planService.findById(id)
                .map(p -> {
                    plan.setIdPlan(id);
                    return ResponseEntity.ok(planService.save(plan));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Integer id) {
        return planService.findById(id)
                .map(p -> {
                    planService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

