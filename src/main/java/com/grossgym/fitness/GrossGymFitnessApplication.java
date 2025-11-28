package com.grossgym.fitness;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Gross Gym Fitness
 * Sistema de gestión de suscripciones para gimnasio
 * 
 * @author Gross Gym Team
 * @version 1.0.0
 */
@Slf4j
@SpringBootApplication
public class GrossGymFitnessApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrossGymFitnessApplication.class, args);
        
        log.info("╔═══════════════════════════════════════════════════════╗");
        log.info("║   GROSS GYM FITNESS - Sistema de Suscripciones       ║");
        log.info("║   Aplicación iniciada correctamente                  ║");
        log.info("║   Puerto: 8080                                        ║");
        log.info("║   Context Path: /api                                  ║");
        log.info("║   Swagger UI: http://localhost:8080/api/swagger-ui   ║");
        log.info("╚═══════════════════════════════════════════════════════╝");
    }
}

