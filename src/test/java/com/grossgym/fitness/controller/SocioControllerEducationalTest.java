package com.grossgym.fitness.controller;

/**
 * ===============================================
 * TESTS DE CONTROLADORES - VERSIÓN EDUCATIVA
 * ===============================================
 * 
 * Este archivo contiene tests COMENTADOS para que aprendas
 * cómo funcionan los tests de controladores REST.
 * 
 * PARA APRENDER: Lee los comentarios y entiende cada sección.
 * PARA EJECUTAR: Descomenta el código cuando quieras probarlos.
 * 
 * ===============================================
 * ¿QUÉ ES UN CONTROLLER?
 * ===============================================
 * 
 * Un Controller es la capa que recibe las peticiones HTTP:
 * - GET → Obtener datos
 * - POST → Crear datos
 * - PUT → Actualizar datos
 * - DELETE → Eliminar datos
 * 
 * Los tests de controller verifican:
 * 1. Que las URLs funcionen correctamente
 * 2. Que los códigos HTTP sean correctos (200, 404, 500, etc.)
 * 3. Que el JSON de respuesta sea correcto
 * 
 * ===============================================
 * HERRAMIENTAS USADAS:
 * ===============================================
 * 
 * 1. @WebMvcTest
 *    - Testea SOLO el controller
 *    - NO carga toda la aplicación (es rápido)
 *    - Mockea automáticamente los servicios
 * 
 * 2. MockMvc
 *    - Simula peticiones HTTP
 *    - No necesita servidor real
 *    - Ejemplo: mockMvc.perform(get("/api/socios"))
 * 
 * 3. @MockBean
 *    - Crea un mock del servicio
 *    - El controller usa este mock en lugar del servicio real
 *    - Evita conectarse a la base de datos
 * 
 * ===============================================
 * EJEMPLO DE TEST DE CONTROLLER:
 * ===============================================
 */

// =========================================================================
// IMPORTS (Descomenta para ejecutar los tests)
// =========================================================================

/*
import com.grossgym.fitness.model.Socio;
import com.grossgym.fitness.service.SocioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
*/

/**
 * @WebMvcTest indica que vamos a testear SOLO el controller
 * Le pasamos la clase del controller específico
 */
/*
@WebMvcTest(SocioController.class)
@DisplayName("Tests Educativos de SocioController - COMENTADOS")
class SocioControllerEducationalTest {

    // ========================================
    // CONFIGURACIÓN
    // ========================================
    
    /**
     * MockMvc es la herramienta principal para testear controllers.
     * Simula peticiones HTTP sin necesidad de un servidor real.
     */
/*
    @Autowired
    private MockMvc mockMvc;
    
    /**
     * @MockBean crea un MOCK del servicio.
     * El controller usará este mock en lugar del servicio real.
     * Así evitamos conectarnos a la base de datos.
     */
/*
    @MockBean
    private SocioService socioService;

    // ========================================
    // TEST 1: GET /api/socios (Listar todos)
    // ========================================
    
    @Test
    @DisplayName("APRENDIENDO: GET /api/socios debe retornar lista de socios")
    void testGetAllSocios() throws Exception {
        // ========================================
        // PASO 1: PREPARAR DATOS DE PRUEBA
        // ========================================
        Socio socio1 = new Socio();
        socio1.setRut("12345678-9");
        socio1.setNombres("Juan");
        socio1.setApellidoPaterno("Pérez");
        
        Socio socio2 = new Socio();
        socio2.setRut("98765432-1");
        socio2.setNombres("María");
        socio2.setApellidoPaterno("Silva");

        // ========================================
        // PASO 2: CONFIGURAR EL MOCK DEL SERVICIO
        // ========================================
        // Cuando el controller llame a findAll(),
        // retornar nuestra lista de prueba
        when(socioService.findAll())
            .thenReturn(Arrays.asList(socio1, socio2));

        // ========================================
        // PASO 3: SIMULAR LA PETICIÓN HTTP
        // ========================================
        mockMvc.perform(get("/api/socios"))  // Hacer GET a /api/socios
            // ========================================
            // PASO 4: VERIFICAR LA RESPUESTA
            // ========================================
            .andExpect(status().isOk())  // Código HTTP 200
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // JSON
            .andExpect(jsonPath("$").isArray())  // Es un array
            .andExpect(jsonPath("$[0].rut").value("12345678-9"))  // Primer elemento
            .andExpect(jsonPath("$[1].rut").value("98765432-1")); // Segundo elemento
        
        // ========================================
        // APRENDIZAJE:
        // ========================================
        // 1. mockMvc.perform() = simula una petición HTTP
        // 2. get("/url") = petición GET
        // 3. andExpect() = verificaciones
        // 4. status().isOk() = código 200
        // 5. jsonPath() = navega el JSON de respuesta
        // 6. $[0] = primer elemento del array
    }

    // ========================================
    // TEST 2: GET /api/socios/{rut} (Buscar por RUT)
    // ========================================
    
    @Test
    @DisplayName("APRENDIENDO: GET /api/socios/{rut} debe retornar un socio")
    void testGetSocioByRut() throws Exception {
        // Preparar
        Socio socio = new Socio();
        socio.setRut("12345678-9");
        socio.setNombres("Juan");
        socio.setCorreo("juan@test.com");

        // Configurar mock
        when(socioService.findById("12345678-9"))
            .thenReturn(Optional.of(socio));

        // Ejecutar y verificar
        mockMvc.perform(get("/api/socios/12345678-9"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.rut").value("12345678-9"))
            .andExpect(jsonPath("$.nombres").value("Juan"))
            .andExpect(jsonPath("$.correo").value("juan@test.com"));
        
        // APRENDIZAJE:
        // - /api/socios/{rut} = URL con parámetro
        // - jsonPath("$.campo") = acceder a un campo del JSON
        // - $ = raíz del JSON
    }

    // ========================================
    // TEST 3: GET con ID no existente (404)
    // ========================================
    
    @Test
    @DisplayName("APRENDIENDO: GET con RUT inexistente debe retornar 404")
    void testGetSocioNotFound() throws Exception {
        // Configurar: cuando busquen este RUT, retornar vacío
        when(socioService.findById("99999999-9"))
            .thenReturn(Optional.empty());

        // Ejecutar y verificar
        mockMvc.perform(get("/api/socios/99999999-9"))
            .andExpect(status().isNotFound());  // Código HTTP 404
        
        // APRENDIZAJE:
        // - Optional.empty() = no encontrado
        // - status().isNotFound() = código 404
        // - Los controladores deben manejar estos casos
    }

    // ========================================
    // TEST 4: POST /api/socios (Crear)
    // ========================================
    
    @Test
    @DisplayName("APRENDIENDO: POST /api/socios debe crear un socio")
    void testCreateSocio() throws Exception {
        // Preparar
        Socio socio = new Socio();
        socio.setRut("12345678-9");
        socio.setNombres("Juan");

        // Configurar mock
        when(socioService.save(any(Socio.class)))
            .thenReturn(socio);

        // JSON que enviaremos en el body
        String socioJson = """
            {
                "rut": "12345678-9",
                "nombres": "Juan",
                "apellidoPaterno": "Pérez",
                "apellidoMaterno": "González",
                "genero": "Masculino",
                "correo": "juan@test.com",
                "celular": "+56912345678",
                "habilitado": true
            }
            """;

        // Ejecutar y verificar
        mockMvc.perform(post("/api/socios")  // POST
                .contentType(MediaType.APPLICATION_JSON)  // Tipo de contenido
                .content(socioJson))  // Body JSON
            .andExpect(status().isOk())  // 200
            .andExpect(jsonPath("$.rut").value("12345678-9"));
        
        // APRENDIZAJE:
        // - post() = petición POST
        // - contentType() = tipo de contenido (JSON)
        // - content() = body de la petición
        // - Usamos JSON en formato String
    }

    // ========================================
    // TEST 5: DELETE /api/socios/{rut}
    // ========================================
    
    @Test
    @DisplayName("APRENDIENDO: DELETE /api/socios/{rut} debe eliminar un socio")
    void testDeleteSocio() throws Exception {
        // Configurar: findById retorna un socio (existe)
        Socio socio = new Socio();
        socio.setRut("12345678-9");
        when(socioService.findById("12345678-9"))
            .thenReturn(Optional.of(socio));

        // Ejecutar y verificar
        mockMvc.perform(delete("/api/socios/12345678-9"))
            .andExpect(status().isNoContent());  // 204 No Content
        
        // APRENDIZAJE:
        // - delete() = petición DELETE
        // - status().isNoContent() = código 204
        // - 204 = operación exitosa sin contenido de respuesta
    }
}
*/

/*
 * ===============================================
 * RESUMEN DE APRENDIZAJES:
 * ===============================================
 * 
 * 1. ANOTACIONES
 *    - @WebMvcTest = testear solo controller
 *    - @MockBean = mockear servicio
 *    - @Autowired = inyectar MockMvc
 * 
 * 2. MOCKMVC - Métodos HTTP
 *    - get() = petición GET
 *    - post() = petición POST
 *    - put() = petición PUT
 *    - delete() = petición DELETE
 * 
 * 3. MOCKMVC - Configuración de Petición
 *    - contentType() = tipo de contenido
 *    - content() = body de la petición
 *    - accept() = tipo de respuesta esperado
 * 
 * 4. MOCKMVC - Verificaciones
 *    - andExpect() = verificar respuesta
 *    - status() = verificar código HTTP
 *    - jsonPath() = verificar JSON
 *    - content() = verificar contenido
 * 
 * 5. CÓDIGOS HTTP COMUNES
 *    - 200 OK = éxito
 *    - 201 Created = creado
 *    - 204 No Content = éxito sin contenido
 *    - 404 Not Found = no encontrado
 *    - 400 Bad Request = petición inválida
 *    - 500 Internal Server Error = error del servidor
 * 
 * 6. JSONPATH
 *    - $ = raíz
 *    - $.campo = campo en la raíz
 *    - $[0] = primer elemento de array
 *    - $[0].campo = campo del primer elemento
 * 
 * ===============================================
 * PARA USAR ESTOS TESTS:
 * ===============================================
 * 
 * 1. Descomenta los imports al inicio
 * 2. Descomenta la clase completa
 * 3. Ejecuta: mvn test -Dtest=SocioControllerEducationalTest
 * 
 * ===============================================
 * DIFERENCIA CON TESTS REALES:
 * ===============================================
 * 
 * En el proyecto, los tests de controllers están en:
 * - SocioControllerTest
 * - PlanControllerTest
 * - etc.
 * 
 * Estos tests son más simples y educativos.
 * Los tests reales pueden tener más complejidad.
 * 
 * ===============================================
 */

