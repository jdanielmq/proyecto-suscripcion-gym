package com.grossgym.fitness.repository;

import com.grossgym.fitness.model.Plan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Tests EDUCATIVOS para PlanRepository
 * 
 * OBJETIVO: Aprender a testear repositories usando Mockito
 * 
 * Los repositories son la capa que interactúa con la base de datos.
 * Spring Data JPA los crea automáticamente, solo definimos la interfaz.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de PlanRepository - Aprendiendo")
class PlanRepositoryTest {

    @Mock
    private PlanRepository planRepository;

    private Plan plan1;
    private Plan plan2;

    @BeforeEach
    void setUp() {
        plan1 = crearPlanPrueba(1, "Mensual");
        plan2 = crearPlanPrueba(2, "Anual");
    }

    // ========================================
    // TEST 1: Save
    // ========================================
    
    @Test
    @DisplayName("APRENDIENDO: save() debe guardar un plan")
    void testSave() {
        // Configurar mock
        when(planRepository.save(any(Plan.class))).thenReturn(plan1);

        // Ejecutar
        Plan resultado = planRepository.save(plan1);

        // Verificar
        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdPlan()).isEqualTo(1);
        assertThat(resultado.getTipoPlan()).isEqualTo("Mensual");
        
        verify(planRepository).save(any(Plan.class));
        
        // APRENDIZAJE:
        // Repository es la capa que guarda datos en la BD
    }
    
    // ========================================
    // TEST 2: FindById
    // ========================================
    
    @Test
    @DisplayName("APRENDIENDO: findById() debe buscar un plan por ID")
    void testFindById() {
        // Configurar
        when(planRepository.findById(1)).thenReturn(Optional.of(plan1));

        // Ejecutar
        Optional<Plan> resultado = planRepository.findById(1);

        // Verificar
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getTipoPlan()).isEqualTo("Mensual");
    }
    
    // ========================================
    // TEST 3: FindAll
    // ========================================
    
    @Test
    @DisplayName("APRENDIENDO: findAll() debe retornar todos los planes")
    void testFindAll() {
        // Configurar
        when(planRepository.findAll()).thenReturn(Arrays.asList(plan1, plan2));

        // Ejecutar
        List<Plan> resultado = planRepository.findAll();

        // Verificar
        assertThat(resultado).hasSize(2);
    }
    
    // ========================================
    // TEST 4: Query Method
    // ========================================
    
    @Test
    @DisplayName("APRENDIENDO: findByIsMatricula() - Query Method")
    void testFindByIsMatricula() {
        // Configurar: buscar planes con matrícula
        when(planRepository.findByIsMatricula(true))
            .thenReturn(Arrays.asList(plan1));

        // Ejecutar
        List<Plan> resultado = planRepository.findByIsMatricula(true);

        // Verificar
        assertThat(resultado).hasSize(1);
        
        // APRENDIZAJE:
        // findByIsMatricula() filtra planes que cobran matrícula
    }
    
    // ========================================
    // HELPER METHOD
    // ========================================
    
    private Plan crearPlanPrueba(Integer id, String descripcion) {
        Plan plan = new Plan();
        plan.setIdPlan(id);
        plan.setTipoPlan(descripcion);
        plan.setUnidad("Mes");
        plan.setDuracion(1);
        plan.setMontoPlan(50000);
        plan.setMontoMatricula(20000);
        plan.setIsMatricula(true);
        return plan;
    }
}

/*
 * ===============================================
 * RESUMEN:
 * ===============================================
 * 
 * Repository = Capa de acceso a datos
 * 
 * Métodos básicos:
 * - save() → Guardar
 * - findById() → Buscar por ID
 * - findAll() → Buscar todos
 * - deleteById() → Eliminar
 * 
 * Query Methods:
 * - Spring crea la query automáticamente
 * - Solo con el nombre del método
 * - Ejemplo: findByIsMatricula()
 * 
 * ===============================================
 */

