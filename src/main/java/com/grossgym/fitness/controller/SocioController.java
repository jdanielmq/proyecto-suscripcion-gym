package com.grossgym.fitness.controller;

import com.grossgym.fitness.model.Socio;
import com.grossgym.fitness.service.SocioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de Socios
 * Expone endpoints para operaciones CRUD de socios
 */
@RestController
@RequestMapping("/socios")
@Tag(name = "Socios", description = "API para gestión de socios del gimnasio")
public class SocioController {

    private final SocioService socioService;

    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }

    /**
     * Obtiene todos los socios
     * GET /api/socios
     */
    @Operation(
        summary = "Listar todos los socios",
        description = "Obtiene una lista completa de todos los socios registrados en el gimnasio"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de socios obtenida exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Socio.class)))
    })
    @GetMapping
    public ResponseEntity<List<Socio>> getAllSocios() {
        List<Socio> socios = socioService.findAll();
        return ResponseEntity.ok(socios);
    }

    /**
     * Obtiene un socio por RUT
     * GET /api/socios/{rut}
     */
    @Operation(
        summary = "Obtener socio por RUT",
        description = "Busca y retorna un socio específico usando su RUT"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Socio encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Socio.class))),
        @ApiResponse(responseCode = "404", description = "Socio no encontrado", content = @Content)
    })
    @GetMapping("/{rut}")
    public ResponseEntity<Socio> getSocioByRut(
            @Parameter(description = "RUT del socio (formato: 12345678-9)", required = true, example = "12345678-9")
            @PathVariable String rut) {
        return socioService.findById(rut)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo socio
     * POST /api/socios
     */
    @Operation(
        summary = "Crear nuevo socio",
        description = "Registra un nuevo socio en el sistema. El RUT debe ser único."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Socio creado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Socio.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o RUT duplicado", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Socio> createSocio(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del socio a crear",
                required = true,
                content = @Content(schema = @Schema(implementation = Socio.class))
            )
            @RequestBody Socio socio) {
        try {
            Socio nuevoSocio = socioService.save(socio);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoSocio);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualiza un socio existente
     * PUT /api/socios/{rut}
     */
    @PutMapping("/{rut}")
    public ResponseEntity<Socio> updateSocio(@PathVariable String rut, @RequestBody Socio socio) {
        return socioService.findById(rut)
                .map(socioExistente -> {
                    socio.setRut(rut);
                    Socio socioActualizado = socioService.save(socio);
                    return ResponseEntity.ok(socioActualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina un socio por RUT
     * DELETE /api/socios/{rut}
     */
    @DeleteMapping("/{rut}")
    public ResponseEntity<Void> deleteSocio(@PathVariable String rut) {
        return socioService.findById(rut)
                .map(socio -> {
                    socioService.deleteById(rut);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene socios por estado (habilitado/deshabilitado)
     * GET /api/socios/habilitado/{estado}
     */
    @GetMapping("/habilitado/{estado}")
    public ResponseEntity<List<Socio>> getSociosByHabilitado(@PathVariable Boolean estado) {
        List<Socio> socios = socioService.findByHabilitado(estado);
        return ResponseEntity.ok(socios);
    }

    /**
     * Busca socios por nombre (contiene)
     * GET /api/socios/buscar?nombre={nombre}
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Socio>> searchSociosByNombre(@RequestParam String nombre) {
        List<Socio> socios = socioService.findByNombresContaining(nombre);
        return ResponseEntity.ok(socios);
    }
}

