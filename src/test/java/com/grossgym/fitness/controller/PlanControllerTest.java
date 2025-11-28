package com.grossgym.fitness.controller;

/*
 * ============================================================================
 * TESTS DE CONTROLLER - PLAN (CÓDIGO EDUCATIVO COMENTADO)
 * ============================================================================
 * 
 * Este archivo muestra cómo testear un controller más simple
 * con menos endpoints que SocioController.
 * 
 * ENFOQUE: Tests más directos y concisos
 * ============================================================================
 */

// IMPORTS (comentados porque el código no se ejecuta)
/*
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grossgym.fitness.model.Plan;
import com.grossgym.fitness.service.PlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
*/

/**
 * DIFERENCIAS con SocioControllerTest:
 * - Menos endpoints (más simple)
 * - IDs numéricos en lugar de RUT (String)
 * - Tests más concisos
 */
//@WebMvcTest(PlanController.class)
//@DisplayName("Tests de PlanController - EDUCATIVO")
class PlanControllerTest {

    /*
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanService planService;

    @Autowired
    private ObjectMapper objectMapper;

    private Plan planMensual;
    private Plan planAnual;

    @BeforeEach
    void setUp() {
        // ========================================
        // PREPARAR DATOS DE PRUEBA
        // ========================================
        
        planMensual = new Plan();
        planMensual.setIdPlan(1);
        planMensual.setTipoPlan("Mensual");
        planMensual.setMontoPlan(35000);
        planMensual.setDuracion(1);
        planMensual.setUnidad("MES");
        planMensual.setIsMatricula(true);
        planMensual.setMontoMatricula(15000);

        planAnual = new Plan();
        planAnual.setIdPlan(2);
        planAnual.setTipoPlan("Anual");
        planAnual.setMontoPlan(300000);
        planAnual.setDuracion(12);
        planAnual.setUnidad("MES");
        planAnual.setIsMatricula(false);
        planAnual.setMontoMatricula(0);
        
        // APRENDIZAJE:
        // - Crear múltiples objetos de prueba en @BeforeEach
        // - Reutilizar en todos los tests
        // - Cada test tiene datos limpios (se ejecuta @BeforeEach cada vez)
    }
    */

    // ========================================
    // EJEMPLO 1: Test Conciso
    // ========================================
    
    /**
     * CONCEPTO: Test en estilo CONCISO
     * - Todo en una sola cadena de métodos
     * - Más fácil de leer cuando hay muchos tests
     */
    /*
    @Test
    @DisplayName("GET /api/planes debe retornar todos los planes")
    void testGetAllPlanes() throws Exception {
        // Arrange, Act y Assert todo junto
        when(planService.findAll()).thenReturn(Arrays.asList(planMensual, planAnual));

        mockMvc.perform(get("/api/planes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].tipoPlan").value("Mensual"))
                .andExpect(jsonPath("$[1].tipoPlan").value("Anual"));

        verify(planService, times(1)).findAll();
        
        // APRENDIZAJE:
        // - Puedes escribir todo en una cadena
        // - Múltiples .andExpect() para verificar varios aspectos
        // - jsonPath("$[0]") = primer elemento del array
        // - jsonPath("$[1]") = segundo elemento del array
    }
    */

    // ========================================
    // EJEMPLO 2: Test con ID Numérico
    // ========================================
    
    /**
     * CONCEPTO: Path variable con números
     * - Similar a RUT pero más simple
     * - Usa Integer en lugar de String
     */
    /*
    @Test
    @DisplayName("GET /api/planes/{id} debe retornar plan por ID")
    void testGetPlanById() throws Exception {
        when(planService.findById(1)).thenReturn(Optional.of(planMensual));

        mockMvc.perform(get("/api/planes/1"))  // ID en la URL
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPlan").value(1))
                .andExpect(jsonPath("$.tipoPlan").value("Mensual"))
                .andExpect(jsonPath("$.montoPlan").value(35000));

        verify(planService, times(1)).findById(1);
        
        // APRENDIZAJE:
        // - /api/planes/1 donde 1 es el ID
        // - findById(1) usa Integer
        // - Puedes verificar múltiples campos del JSON
    }
    */

    // ========================================
    // EJEMPLO 3: Verificar Campos Específicos
    // ========================================
    
    /**
     * CONCEPTO: Verificar datos específicos del modelo
     * - Campos numéricos (montoPlan)
     * - Campos booleanos (isMatricula)
     */
    /*
    @Test
    @DisplayName("POST /api/planes debe crear plan con todos los campos")
    void testCreatePlanConTodosLosCampos() throws Exception {
        when(planService.save(any(Plan.class))).thenReturn(planMensual);

        mockMvc.perform(post("/api/planes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(planMensual)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipoPlan").value("Mensual"))
                .andExpect(jsonPath("$.montoPlan").value(35000))
                .andExpect(jsonPath("$.montoMatricula").value(15000))
                .andExpect(jsonPath("$.duracion").value(1))
                .andExpect(jsonPath("$.unidad").value("MES"))
                .andExpect(jsonPath("$.isMatricula").value(true));
        
        // APRENDIZAJE:
        // - jsonPath() puede verificar CUALQUIER campo
        // - .value(35000) para números
        // - .value(true) para booleanos
        // - .value("MES") para strings
    }
    */

    // ========================================
    // EJEMPLO 4: Test de Update
    // ========================================
    
    /**
     * CONCEPTO: Actualizar con cambios en los datos
     * - Modificar objeto antes de actualizar
     * - Verificar que los cambios se reflejan
     */
    /*
    @Test
    @DisplayName("PUT /api/planes/{id} debe actualizar monto del plan")
    void testUpdatePlanMonto() throws Exception {
        // Modificar el monto para el test
        planMensual.setMontoPlan(40000);
        
        when(planService.findById(1)).thenReturn(Optional.of(planMensual));
        when(planService.save(any(Plan.class))).thenReturn(planMensual);

        mockMvc.perform(put("/api/planes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(planMensual)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.montoPlan").value(40000));
        
        // APRENDIZAJE:
        // - Puedes modificar los datos de prueba en cada test
        // - Verifica que el cambio específico se aplicó
        // - PUT típicamente retorna 200 OK
    }
    */

    // ========================================
    // EJEMPLO 5: Test de Delete Simple
    // ========================================
    
    /**
     * CONCEPTO: Delete más simple
     * - Solo verificar status code
     * - No hay body en el response
     */
    /*
    @Test
    @DisplayName("DELETE /api/planes/{id} debe eliminar plan")
    void testDeletePlan() throws Exception {
        when(planService.findById(1)).thenReturn(Optional.of(planMensual));
        doNothing().when(planService).deleteById(1);

        mockMvc.perform(delete("/api/planes/1"))
                .andExpect(status().isNoContent());  // Solo 204, sin JSON

        verify(planService).findById(1);
        verify(planService).deleteById(1);
        
        // APRENDIZAJE:
        // - DELETE retorna 204 No Content (sin body)
        // - Solo verificas el status code
        // - verify() sin times() = verify(mock, times(1))
    }
    */

    // ========================================
    // EJEMPLO 6: Test de Error 404
    // ========================================
    
    /**
     * CONCEPTO: Testear casos de error
     * - Recurso no encontrado
     * - Verificar solo status code
     */
    /*
    @Test
    @DisplayName("GET /api/planes/{id} debe retornar 404 si no existe")
    void testGetPlanByIdNotFound() throws Exception {
        when(planService.findById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/planes/999"))
                .andExpect(status().isNotFound());  // 404

        verify(planService).findById(999);
        
        // APRENDIZAJE:
        // - Optional.empty() → 404
        // - No necesitas verificar JSON (no hay body en 404)
        // - Importante testear casos de error también
    }
    */
}

/*
 * ============================================================================
 * COMPARACIÓN: SocioController vs PlanController
 * ============================================================================
 * 
 * SIMILITUDES:
 * - Mismo patrón de tests (GET, POST, PUT, DELETE)
 * - Mismo uso de MockMvc
 * - Mismas verificaciones de status codes
 * - Mismo uso de jsonPath()
 * 
 * DIFERENCIAS:
 * - Socio usa RUT (String) como ID
 * - Plan usa ID numérico (Integer)
 * - Socio tiene más campos
 * - Plan tiene menos endpoints
 * 
 * ============================================================================
 * CONCEPTOS NUEVOS EN ESTE ARCHIVO:
 * ============================================================================
 * 
 * 1. Tests Concisos
 *    - Todo en una cadena
 *    - Menos separación de pasos
 *    - Más rápido de escribir
 * 
 * 2. Verificar Múltiples Campos
 *    - Varios .andExpect(jsonPath()...)
 *    - Para validar objeto completo
 * 
 * 3. Tipos de Datos en JSON
 *    - Numbers: .value(35000)
 *    - Booleans: .value(true)
 *    - Strings: .value("texto")
 * 
 * 4. Arrays en JSON
 *    - $[0] = primer elemento
 *    - $[1] = segundo elemento
 *    - $[N] = elemento N
 * 
 * 5. Modificar Datos en Tests
 *    - Cambiar objeto antes del test
 *    - Para testear actualizaciones
 * 
 * ============================================================================
 * EJERCICIO MENTAL:
 * ============================================================================
 * 
 * Intenta responder:
 * 1. ¿Cuál es la diferencia entre isOk() e isCreated()?
 * 2. ¿Cuándo usarías isNoContent()?
 * 3. ¿Qué hace doNothing().when()?
 * 4. ¿Por qué usamos Optional.empty() para 404?
 * 5. ¿Qué significa verify(mock) vs verify(mock, times(1))?
 * 
 * Respuestas:
 * 1. isOk() = 200, isCreated() = 201 (para recursos nuevos)
 * 2. isNoContent() = 204 (para DELETE exitoso sin body)
 * 3. Configura un mock de método void para no hacer nada
 * 4. Porque el controller retorna 404 cuando Optional está vacío
 * 5. Son equivalentes (verify sin times asume times(1))
 * 
 * ============================================================================
 */

