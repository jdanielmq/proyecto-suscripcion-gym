package com.grossgym.fitness.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Tests EDUCATIVOS para CorsConfig
 * 
 * IMPORTANTE: Las clases de configuración (@Configuration) son DIFÍCILES
 * de testear porque dependen del framework de Spring.
 * 
 * Estos tests son MUY SIMPLES y EDUCATIVOS.
 * En proyectos reales, la configuración se verifica con:
 * - Tests de integración
 * - Tests end-to-end
 * - Pruebas manuales
 * 
 * OBJETIVO: Aprender conceptos básicos de testing de configuración
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de CorsConfig - Aprendiendo Configuración")
class CorsConfigTest {

    // ========================================
    // TEST 1: Verificar que el método existe
    // ========================================
    
    @Test
    @DisplayName("APRENDIENDO: CorsConfig debe tener el método addCorsMappings")
    void testCorsConfigTieneMetodoAddCorsMappings() {
        // PASO 1: Crear instancia de la configuración
        CorsConfig corsConfig = new CorsConfig();

        // PASO 2: Verificar que no es null (se puede instanciar)
        assertThat(corsConfig).isNotNull();
        
        // APRENDIZAJE:
        // - Este es el test MÁS BÁSICO
        // - Solo verifica que la clase existe y se puede crear
        // - En tests reales esto no es muy útil, pero es un inicio
    }

    // ========================================
    // TEST 2: Verificar que el método se ejecuta
    // ========================================
    
    @Test
    @DisplayName("APRENDIENDO: addCorsMappings debe ejecutarse sin errores")
    void testAddCorsMappingsSeEjecutaSinErrores() {
        // PASO 1: Crear la configuración
        CorsConfig corsConfig = new CorsConfig();

        // PASO 2: Crear MOCKS (objetos falsos)
        CorsRegistry registry = mock(CorsRegistry.class, RETURNS_DEEP_STUBS);
        // RETURNS_DEEP_STUBS permite mockear cadenas de métodos
        // como registry.addMapping().allowedOriginPatterns()...

        // PASO 3: Llamar al método
        corsConfig.addCorsMappings(registry);

        // PASO 4: Verificar que se llamó addMapping
        verify(registry, atLeastOnce()).addMapping(anyString());
        
        // APRENDIZAJE:
        // - RETURNS_DEEP_STUBS permite mockear fluent API
        // - atLeastOnce() = se llamó al menos 1 vez
        // - anyString() = acepta cualquier String
        // 
        // NOTA: Este test es SIMPLE porque las configuraciones
        // son difíciles de testear con mocks. En la práctica,
        // se verifican con tests de integración.
    }

    // ========================================
    // TEST 3: Verificar propiedades de la clase
    // ========================================
    
    @Test
    @DisplayName("APRENDIENDO: CorsConfig debe implementar WebMvcConfigurer")
    void testCorsConfigImplementaWebMvcConfigurer() {
        // PASO 1: Crear instancia
        CorsConfig corsConfig = new CorsConfig();

        // PASO 2: Verificar que implementa la interfaz correcta
        assertThat(corsConfig)
            .isInstanceOf(org.springframework.web.servlet.config.annotation.WebMvcConfigurer.class);
        
        // APRENDIZAJE:
        // - isInstanceOf() verifica que un objeto es de cierto tipo
        // - Esto asegura que CorsConfig implementa WebMvcConfigurer
        // - Es importante para que Spring lo reconozca como configuración
    }
}

/*
 * ===============================================
 * RESUMEN DE APRENDIZAJES:
 * ===============================================
 * 
 * 1. ¿Por qué es difícil testear Config?
 *    - Dependen del framework de Spring
 *    - No tienen lógica de negocio
 *    - Son principalmente declarativas
 * 
 * 2. ¿Cómo testear Config entonces?
 *    - Con MOCKS (objetos falsos)
 *    - Verificando que se llaman los métodos correctos
 *    - Con tests de integración
 * 
 * 3. Mockito
 *    - mock() → Crea objeto falso
 *    - when().thenReturn() → Define comportamiento
 *    - verify() → Verifica que se llamó un método
 *    - any(), anyString(), anyBoolean() → Matchers
 * 
 * 4. AssertJ
 *    - isNotNull() → Verifica que existe
 *    - isInstanceOf() → Verifica el tipo
 * 
 * 5. En la práctica
 *    - Las configuraciones raramente se testean así
 *    - Se verifican con tests de integración
 *    - O con pruebas manuales del sistema
 * 
 * ===============================================
 * NOTA IMPORTANTE:
 * ===============================================
 * 
 * Estos tests son EDUCATIVOS. En proyectos reales:
 * - Config se testea indirectamente
 * - Tests de integración verifican que CORS funciona
 * - No vale la pena el esfuerzo de mockear todo
 * 
 * PERO es importante saber CÓMO se haría si fuera necesario.
 * ===============================================
 */

