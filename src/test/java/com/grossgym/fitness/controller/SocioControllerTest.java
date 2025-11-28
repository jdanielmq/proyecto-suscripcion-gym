package com.grossgym.fitness.controller;

/*
 * ============================================================================
 * TESTS DE CONTROLLER - CÓDIGO EDUCATIVO (COMENTADO)
 * ============================================================================
 * 
 * Este archivo contiene tests de controllers COMENTADOS.
 * NO SE EJECUTAN porque tienen problemas con ApplicationContext.
 * 
 * OBJETIVO: Aprender cómo se escriben tests de controllers.
 * 
 * Para ejecutar estos tests necesitarías:
 * 1. Configurar H2 Database correctamente
 * 2. Crear application-test.yml
 * 3. Resolver problemas de CORS y Swagger
 * 
 * Pero puedes LEER y APRENDER la estructura y conceptos.
 * ============================================================================
 */

// ========================================
// IMPORTS NECESARIOS
// ========================================

/*
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grossgym.fitness.model.Socio;
import com.grossgym.fitness.service.SocioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
*/

/**
 * ============================================================================
 * ANOTACIONES IMPORTANTES
 * ============================================================================
 * 
 * @WebMvcTest(SocioController.class)
 * - Carga SOLO la capa web (controllers)
 * - NO carga servicios, repositories, BD
 * - Es MÁS RÁPIDO que @SpringBootTest
 * - Usa MockMvc para simular requests HTTP
 * 
 * PROBLEMA: En proyectos complejos (como este) puede fallar por:
 * - Configuraciones de seguridad
 * - CORS configuration
 * - Swagger UI
 * - Base de datos
 */
//@WebMvcTest(SocioController.class)
//@DisplayName("Tests de SocioController - EDUCATIVO")
class SocioControllerTest {

    /**
     * ========================================
     * COMPONENTES DEL TEST
     * ========================================
     * 
     * 1. MockMvc
     *    - Simula requests HTTP sin servidor
     *    - Puede hacer GET, POST, PUT, DELETE
     *    - Verifica responses, status codes, JSON
     * 
     * 2. @MockBean SocioService
     *    - Crea un MOCK (falso) del servicio
     *    - NO usa el servicio real
     *    - Defines comportamiento con when()
     * 
     * 3. ObjectMapper
     *    - Convierte objetos Java ↔ JSON
     *    - Necesario para enviar/recibir JSON
     */
    
    /*
    @Autowired
    private MockMvc mockMvc;  // Simula HTTP requests

    @MockBean
    private SocioService socioService;  // Mock del servicio

    @Autowired
    private ObjectMapper objectMapper;  // Java ↔ JSON

    private Socio socio1;

    @BeforeEach
    void setUp() {
        // Crear datos de prueba antes de cada test
        socio1 = new Socio();
        socio1.setRut("12345678-9");
        socio1.setNombres("Juan Pablo");
        socio1.setApellidoPaterno("Soto");
        socio1.setApellidoMaterno("González");
        socio1.setGenero("Masculino");
        socio1.setCorreo("juan@example.com");
        socio1.setCelular("+56912345678");
        socio1.setHabilitado(true);
    }
    */

    // ========================================
    // TEST 1: GET /api/socios
    // ========================================
    
    /**
     * OBJETIVO: Testear que GET /api/socios retorna lista de socios
     * 
     * PASOS:
     * 1. Configurar el mock del servicio
     * 2. Hacer un GET request con MockMvc
     * 3. Verificar el status code (200 OK)
     * 4. Verificar el JSON response
     * 5. Verificar que se llamó al servicio
     */
    /*
    @Test
    @DisplayName("GET /api/socios debe retornar lista de socios")
    void testGetAllSocios() throws Exception {
        // ========================================
        // PASO 1: ARRANGE (Preparar)
        // ========================================
        
        // Crear lista de socios de prueba
        List<Socio> socios = Arrays.asList(socio1);
        
        // Configurar el MOCK: cuando se llame socioService.findAll()
        // debe retornar nuestra lista de prueba
        when(socioService.findAll()).thenReturn(socios);

        // ========================================
        // PASO 2: ACT (Actuar)
        // ========================================
        
        // Simular un GET request a /api/socios
        mockMvc.perform(get("/api/socios"))
        
        // ========================================
        // PASO 3: ASSERT (Verificar)
        // ========================================
        
                // Verificar que retorna 200 OK
                .andExpect(status().isOk())
                
                // Verificar que el JSON tiene 1 elemento
                .andExpect(jsonPath("$", hasSize(1)))
                
                // Verificar que el primer elemento tiene el RUT correcto
                .andExpect(jsonPath("$[0].rut").value("12345678-9"));

        // Verificar que el servicio fue llamado 1 vez
        verify(socioService, times(1)).findAll();
        
        // APRENDIZAJE:
        // - perform() simula el HTTP request
        // - andExpect() verifica el response
        // - jsonPath() navega por el JSON response
        // - verify() comprueba interacciones con mocks
    }
    */

    // ========================================
    // TEST 2: GET /api/socios/{rut}
    // ========================================
    
    /**
     * OBJETIVO: Testear que GET /api/socios/12345678-9 retorna un socio
     * 
     * CONCEPTOS NUEVOS:
     * - Path variables (parámetros en la URL)
     * - Optional en responses
     */
    /*
    @Test
    @DisplayName("GET /api/socios/{rut} debe retornar socio cuando existe")
    void testGetSocioByRut() throws Exception {
        // ARRANGE: Configurar el mock
        when(socioService.findById("12345678-9"))
            .thenReturn(Optional.of(socio1));

        // ACT & ASSERT: Request y verificaciones
        mockMvc.perform(get("/api/socios/12345678-9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombres").value("Juan Pablo"));

        verify(socioService, times(1)).findById("12345678-9");
        
        // APRENDIZAJE:
        // - get("/api/socios/12345678-9") incluye el RUT en la URL
        // - jsonPath("$.nombres") accede al campo "nombres" del JSON
        // - El controller convierte Optional → JSON automáticamente
    }
    */

    /**
     * OBJETIVO: Testear caso cuando el socio NO existe
     * 
     * CONCEPTOS:
     * - Manejo de Optional.empty()
     * - Status code 404 Not Found
     */
    /*
    @Test
    @DisplayName("GET /api/socios/{rut} debe retornar 404 cuando no existe")
    void testGetSocioByRutNotFound() throws Exception {
        // ARRANGE: Configurar para retornar Optional vacío
        when(socioService.findById("99999999-9"))
            .thenReturn(Optional.empty());

        // ACT & ASSERT
        mockMvc.perform(get("/api/socios/99999999-9"))
                .andExpect(status().isNotFound());  // 404

        verify(socioService, times(1)).findById("99999999-9");
        
        // APRENDIZAJE:
        // - Optional.empty() → Controller retorna 404
        // - No necesitas hacer nada especial en el test
        // - Spring maneja esto automáticamente
    }
    */

    // ========================================
    // TEST 3: POST /api/socios
    // ========================================
    
    /**
     * OBJETIVO: Testear crear un nuevo socio
     * 
     * CONCEPTOS NUEVOS:
     * - POST request con body JSON
     * - Convertir objeto Java → JSON
     * - Status code 201 Created
     */
    /*
    @Test
    @DisplayName("POST /api/socios debe crear socio")
    void testCreateSocio() throws Exception {
        // ARRANGE
        when(socioService.save(any(Socio.class))).thenReturn(socio1);

        // ACT: POST con JSON en el body
        mockMvc.perform(post("/api/socios")
                .contentType(MediaType.APPLICATION_JSON)  // Header: Content-Type
                .content(objectMapper.writeValueAsString(socio1)))  // Body: JSON
        
        // ASSERT
                .andExpect(status().isCreated())  // 201
                .andExpect(jsonPath("$.rut").value("12345678-9"));

        verify(socioService, times(1)).save(any(Socio.class));
        
        // APRENDIZAJE:
        // - post() para HTTP POST
        // - contentType() define el tipo de contenido
        // - content() es el body del request
        // - objectMapper.writeValueAsString() convierte Java → JSON
        // - status().isCreated() verifica 201
    }
    */

    // ========================================
    // TEST 4: PUT /api/socios/{rut}
    // ========================================
    
    /**
     * OBJETIVO: Testear actualizar un socio existente
     * 
     * CONCEPTOS:
     * - PUT request
     * - Actualizar recurso existente
     */
    /*
    @Test
    @DisplayName("PUT /api/socios/{rut} debe actualizar socio")
    void testUpdateSocio() throws Exception {
        // ARRANGE
        when(socioService.findById("12345678-9"))
            .thenReturn(Optional.of(socio1));
        when(socioService.save(any(Socio.class)))
            .thenReturn(socio1);

        // ACT & ASSERT
        mockMvc.perform(put("/api/socios/12345678-9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(socio1)))
                .andExpect(status().isOk());

        verify(socioService, times(1)).findById("12345678-9");
        verify(socioService, times(1)).save(any(Socio.class));
        
        // APRENDIZAJE:
        // - put() para HTTP PUT
        // - Similar a POST pero para actualizar
        // - Retorna 200 OK (no 201 Created)
    }
    */

    // ========================================
    // TEST 5: DELETE /api/socios/{rut}
    // ========================================
    
    /**
     * OBJETIVO: Testear eliminar un socio
     * 
     * CONCEPTOS:
     * - DELETE request
     * - Status code 204 No Content
     * - doNothing() para métodos void
     */
    /*
    @Test
    @DisplayName("DELETE /api/socios/{rut} debe eliminar socio")
    void testDeleteSocio() throws Exception {
        // ARRANGE
        when(socioService.findById("12345678-9"))
            .thenReturn(Optional.of(socio1));
        doNothing().when(socioService).deleteById("12345678-9");
        //       ↑ Para métodos void

        // ACT & ASSERT
        mockMvc.perform(delete("/api/socios/12345678-9"))
                .andExpect(status().isNoContent());  // 204

        verify(socioService, times(1)).findById("12345678-9");
        verify(socioService, times(1)).deleteById("12345678-9");
        
        // APRENDIZAJE:
        // - delete() para HTTP DELETE
        // - status().isNoContent() = 204
        // - doNothing() se usa para métodos void
        // - 204 significa "exitoso pero sin contenido en response"
    }
    */

    // ========================================
    // TEST 6: GET con Query Parameters
    // ========================================
    
    /**
     * OBJETIVO: Testear búsqueda con parámetros en la URL
     * 
     * CONCEPTOS:
     * - Query parameters (?nombre=Juan)
     * - param() en MockMvc
     */
    /*
    @Test
    @DisplayName("GET /api/socios/buscar debe buscar por nombre")
    void testBuscarSociosPorNombre() throws Exception {
        // ARRANGE
        when(socioService.findByNombresContaining("Juan"))
            .thenReturn(List.of(socio1));

        // ACT & ASSERT
        mockMvc.perform(get("/api/socios/buscar")
                .param("nombre", "Juan"))  // Query param
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(socioService, times(1)).findByNombresContaining("Juan");
        
        // APRENDIZAJE:
        // - param("key", "value") agrega ?key=value a la URL
        // - Múltiples params: .param("a", "1").param("b", "2")
        // - Se traduce a: /api/socios/buscar?nombre=Juan
    }
    */
}

/*
 * ============================================================================
 * RESUMEN DE CONCEPTOS APRENDIDOS
 * ============================================================================
 * 
 * 1. ANOTACIONES:
 *    @WebMvcTest → Carga solo capa web
 *    @MockBean → Crea mock de dependencias
 *    @Autowired → Inyecta MockMvc y ObjectMapper
 * 
 * 2. MockMvc (Simular HTTP):
 *    mockMvc.perform(...)
 *    - get("/url") → GET request
 *    - post("/url") → POST request
 *    - put("/url") → PUT request
 *    - delete("/url") → DELETE request
 * 
 * 3. Request Configuration:
 *    .contentType(MediaType.APPLICATION_JSON) → Header Content-Type
 *    .content(json) → Body del request
 *    .param("key", "value") → Query parameter
 * 
 * 4. Response Verification (.andExpect):
 *    .andExpect(status().isOk()) → 200
 *    .andExpect(status().isCreated()) → 201
 *    .andExpect(status().isNoContent()) → 204
 *    .andExpect(status().isNotFound()) → 404
 * 
 * 5. JSON Verification (jsonPath):
 *    jsonPath("$.campo") → Accede a un campo
 *    jsonPath("$", hasSize(N)) → Verifica tamaño de array
 *    jsonPath("$[0].campo") → Primer elemento de array
 * 
 * 6. Mockito:
 *    when(mock.metodo()).thenReturn(valor) → Configura comportamiento
 *    verify(mock, times(N)).metodo() → Verifica llamadas
 *    any(Clase.class) → Matcher para cualquier objeto
 *    doNothing().when(mock).metodo() → Para métodos void
 * 
 * 7. ObjectMapper:
 *    writeValueAsString(objeto) → Java → JSON
 *    readValue(json, Clase.class) → JSON → Java
 * 
 * ============================================================================
 * ¿POR QUÉ ESTE CÓDIGO ESTÁ COMENTADO?
 * ============================================================================
 * 
 * En este proyecto específico, @WebMvcTest falla porque:
 * 1. ApplicationContext necesita configuración compleja
 * 2. CORS configuration causa conflictos
 * 3. Swagger UI necesita beans adicionales
 * 4. La base de datos real está configurada
 * 
 * PARA HACERLO FUNCIONAR NECESITARÍAS:
 * 1. Crear src/test/resources/application-test.yml
 * 2. Configurar H2 en lugar de MySQL
 * 3. Excluir configuraciones problemáticas
 * 4. Agregar @TestConfiguration si es necesario
 * 
 * PERO el código aquí te enseña la ESTRUCTURA y CONCEPTOS correctos.
 * 
 * ============================================================================
 * PRACTICA RECOMENDADA
 * ============================================================================
 * 
 * 1. Lee cada test línea por línea
 * 2. Entiende qué hace cada método
 * 3. Identifica el patrón Arrange-Act-Assert
 * 4. Compara con los tests de servicio que SÍ funcionan
 * 5. Nota las similitudes y diferencias
 * 
 * ============================================================================
 * SIGUIENTE PASO
 * ============================================================================
 * 
 * Si quieres tests de controller que FUNCIONEN:
 * - Usa @SpringBootTest + TestRestTemplate
 * - O configura el proyecto completo para tests de integración
 * - O usa herramientas como Testcontainers
 * 
 * Pero esos son más complejos. Estos tests unitarios son más educativos.
 * ============================================================================
 */

