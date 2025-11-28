package com.grossgym.fitness.repository;

import com.grossgym.fitness.model.Socio;
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
import static org.mockito.Mockito.*;

/**
 * Tests EDUCATIVOS para SocioRepository
 * 
 * IMPORTANTE: Estos tests usan MOCKS (simulaciones) en lugar de una BD real.
 * 
 * ¿Por qué? Porque @DataJpaTest requiere configuración compleja de Spring.
 * 
 * OBJETIVO: Aprender los CONCEPTOS de testing de repositories
 * sin la complejidad de configurar Spring + H2.
 * 
 * En proyectos reales usarías @DataJpaTest, pero aquí usamos
 * Mockito puro para que sea MÁS FÁCIL de entender.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de SocioRepository - Aprendiendo Repositories")
class SocioRepositoryTest {

    /**
     * @Mock crea un MOCK (simulación) del repository
     * No es el repository real, es una versión falsa para testing
     */
    @Mock
    private SocioRepository socioRepository;

    private Socio socio1;
    private Socio socio2;

    @BeforeEach
    void setUp() {
        // Preparar datos de prueba
        socio1 = crearSocioPrueba("12345678-9", "Juan");
        socio2 = crearSocioPrueba("98765432-1", "María");
    }

    // ========================================
    // TEST 1: Save (Guardar)
    // ========================================
    
    @Test
    @DisplayName("APRENDIENDO: save() debe guardar un socio")
    void testSave() {
        // ========================================
        // PASO 1: CONFIGURAR EL MOCK
        // ========================================
        // Decimos: "cuando llamen a save() con cualquier Socio,
        // retorna socio1"
        when(socioRepository.save(any(Socio.class))).thenReturn(socio1);

        // ========================================
        // PASO 2: EJECUTAR EL MÉTODO
        // ========================================
        Socio resultado = socioRepository.save(socio1);

        // ========================================
        // PASO 3: VERIFICAR EL RESULTADO
        // ========================================
        assertThat(resultado).isNotNull();
        assertThat(resultado.getRut()).isEqualTo("12345678-9");
        assertThat(resultado.getNombres()).isEqualTo("Juan");
        
        // ========================================
        // PASO 4: VERIFICAR QUE SE LLAMÓ
        // ========================================
        verify(socioRepository, times(1)).save(any(Socio.class));
        
        // ========================================
        // APRENDIZAJE:
        // ========================================
        // 1. when().thenReturn() = configurar comportamiento del mock
        // 2. save() retorna la entidad guardada
        // 3. verify() confirma que se llamó al método
        // 4. any(Socio.class) = acepta cualquier Socio
    }
    
    // ========================================
    // TEST 2: FindById (Buscar por ID)
    // ========================================
    
    @Test
    @DisplayName("APRENDIENDO: findById() debe buscar un socio por RUT")
    void testFindById() {
        // Configurar: cuando busquen por "12345678-9", retornar socio1
        when(socioRepository.findById("12345678-9"))
            .thenReturn(Optional.of(socio1));

        // Ejecutar
        Optional<Socio> resultado = socioRepository.findById("12345678-9");

        // Verificar
        assertThat(resultado).isPresent();  // Tiene valor
        assertThat(resultado.get().getNombres()).isEqualTo("Juan");
        
        verify(socioRepository).findById("12345678-9");
        
        // APRENDIZAJE:
        // - findById() retorna Optional<Entity>
        // - Optional.of() = Optional con valor
        // - isPresent() = true si tiene valor
        // - get() obtiene el valor del Optional
    }
    
    @Test
    @DisplayName("APRENDIENDO: findById() debe retornar vacío si no existe")
    void testFindByIdNotFound() {
        // Configurar: cuando busquen por RUT que no existe, retornar vacío
        when(socioRepository.findById("99999999-9"))
            .thenReturn(Optional.empty());

        // Ejecutar
        Optional<Socio> resultado = socioRepository.findById("99999999-9");

        // Verificar
        assertThat(resultado).isEmpty();  // No tiene valor
        
        // APRENDIZAJE:
        // - Optional.empty() = Optional sin valor
        // - isEmpty() = true si no tiene valor
        // - Esto representa "no encontrado" en la BD
    }

    // ========================================
    // TEST 3: FindAll (Buscar Todos)
    // ========================================
    
    @Test
    @DisplayName("APRENDIENDO: findAll() debe retornar todos los socios")
    void testFindAll() {
        // Configurar: retornar lista de 2 socios
        List<Socio> socios = Arrays.asList(socio1, socio2);
        when(socioRepository.findAll()).thenReturn(socios);

        // Ejecutar
        List<Socio> resultado = socioRepository.findAll();

        // Verificar
        assertThat(resultado).hasSize(2);
        assertThat(resultado).contains(socio1, socio2);
        
        // APRENDIZAJE:
        // - findAll() retorna List<Entity>
        // - hasSize(N) verifica cantidad
        // - contains() verifica que incluye elementos específicos
    }

    // ========================================
    // TEST 4: Query Method Personalizado
    // ========================================
    
    @Test
    @DisplayName("APRENDIENDO: findByHabilitado() - Query Method personalizado")
    void testFindByHabilitado() {
        // Configurar: cuando busquen habilitados, retornar lista
        when(socioRepository.findByHabilitado(true))
            .thenReturn(Arrays.asList(socio1, socio2));

        // Ejecutar
        List<Socio> resultado = socioRepository.findByHabilitado(true);

        // Verificar
        assertThat(resultado).hasSize(2);
        assertThat(resultado).allMatch(s -> s.getHabilitado());
        
        // APRENDIZAJE:
        // - findByHabilitado() es un QUERY METHOD
        // - Spring Data JPA lo implementa automáticamente
        // - Solo con el nombre: findBy + NombreCampo
        // - Ejemplo: findByNombres, findByCorreo, etc.
        // - allMatch() verifica que todos cumplen condición
    }

    // ========================================
    // TEST 5: DeleteById (Eliminar)
    // ========================================
    
    @Test
    @DisplayName("APRENDIENDO: deleteById() debe eliminar un socio")
    void testDeleteById() {
        // Configurar: deleteById no retorna nada (void)
        doNothing().when(socioRepository).deleteById("12345678-9");

        // Ejecutar
        socioRepository.deleteById("12345678-9");

        // Verificar que se llamó
        verify(socioRepository, times(1)).deleteById("12345678-9");
        
        // APRENDIZAJE:
        // - doNothing() se usa para métodos void
        // - deleteById() no retorna nada
        // - Solo verificamos que se llamó el método
    }

    // ========================================
    // TEST 6: Query Method con Containing
    // ========================================
    
    @Test
    @DisplayName("APRENDIENDO: findByNombresContaining() - Búsqueda con LIKE")
    void testFindByNombresContaining() {
        // Configurar: buscar nombres que contengan "Juan"
        Socio juanPablo = crearSocioPrueba("11111111-1", "Juan Pablo");
        when(socioRepository.findByNombresContaining("Juan"))
            .thenReturn(Arrays.asList(juanPablo));

        // Ejecutar
        List<Socio> resultado = socioRepository.findByNombresContaining("Juan");

        // Verificar
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombres()).contains("Juan");
        
        // APRENDIZAJE:
        // - findByNombresContaining() es otro QUERY METHOD
        // - "Containing" = SQL LIKE '%texto%'
        // - Spring crea la query automáticamente
        // - Útil para búsquedas parciales
        // 
        // OTROS QUERY METHODS ÚTILES:
        // - findByNombresStartingWith() = LIKE 'texto%'
        // - findByNombresEndingWith() = LIKE '%texto'
        // - findByNombresIgnoreCase() = sin importar mayúsculas
    }

    // ========================================
    // MÉTODO AUXILIAR (Helper Method)
    // ========================================
    
    /**
     * Método helper para crear socios de prueba rápidamente.
     * Evita repetir código en cada test.
     */
    private Socio crearSocioPrueba(String rut, String nombres) {
        Socio socio = new Socio();
        socio.setRut(rut);
        socio.setNombres(nombres);
        socio.setApellidoPaterno("Apellido");
        socio.setApellidoMaterno("Materno");
        socio.setGenero("Masculino");
        socio.setCorreo(nombres.toLowerCase() + "@test.com");
        socio.setCelular("+56900000000");
        socio.setHabilitado(true);
        return socio;
    }
}

/*
 * ===============================================
 * RESUMEN DE APRENDIZAJES:
 * ===============================================
 * 
 * 1. MOCKS (@Mock)
 *    - Simulan el comportamiento de un objeto
 *    - NO son objetos reales
 *    - Se usan para aislar el código que queremos testear
 *    - Ejemplo: Mock del repository para testear servicio
 * 
 * 2. Mockito - Configurar Comportamiento
 *    - when(mock.metodo()).thenReturn(valor)
 *    - Cuando llamen a metodo(), retornar valor
 *    - any(Clase.class) = cualquier objeto de esa clase
 *    - anyString() = cualquier String
 * 
 * 3. Mockito - Verificar Llamadas
 *    - verify(mock).metodo()
 *    - Verifica que se llamó al método
 *    - times(N) = se llamó N veces
 *    - never() = nunca se llamó
 * 
 * 4. Spring Data JPA - Métodos Estándar
 *    - save(entity) → Guardar/actualizar
 *    - findById(id) → Buscar por ID (retorna Optional)
 *    - findAll() → Buscar todos (retorna List)
 *    - deleteById(id) → Eliminar por ID
 * 
 * 5. Spring Data JPA - Query Methods
 *    - findByNombreCampo() → Buscar por campo
 *    - findByNombresContaining() → LIKE '%texto%'
 *    - findByHabilitado() → Buscar por boolean
 *    - Spring crea la SQL automáticamente
 * 
 * 6. Optional
 *    - Optional.of(valor) → Optional con valor
 *    - Optional.empty() → Optional vacío
 *    - isPresent() → tiene valor?
 *    - isEmpty() → está vacío?
 *    - get() → obtener el valor
 * 
 * 7. AssertJ
 *    - assertThat(resultado).isNotNull()
 *    - assertThat(resultado).hasSize(N)
 *    - assertThat(resultado).contains(elemento)
 *    - assertThat(resultado).allMatch(condicion)
 * 
 * ===============================================
 * ¿POR QUÉ USAMOS MOCKS EN LUGAR DE @DataJpaTest?
 * ===============================================
 * 
 * @DataJpaTest requiere:
 * - Configurar Spring Boot Test
 * - Configurar H2 Database
 * - Cargar ApplicationContext
 * - Puede fallar por conflictos de configuración
 * 
 * Mocks son:
 * - MÁS SIMPLES de entender
 * - NO requieren configuración de Spring
 * - MÁS RÁPIDOS de ejecutar
 * - PERFECTOS para aprender conceptos
 * 
 * ===============================================
 * DIFERENCIA: Repository Tests vs Service Tests
 * ===============================================
 * 
 * SERVICE TESTS:
 * - Mockean el REPOSITORY
 * - Testean la LÓGICA DE NEGOCIO
 * - Verifican validaciones, cálculos
 * 
 * REPOSITORY TESTS:
 * - Testean las QUERIES
 * - Verifican que Spring Data funciona
 * - En proyectos reales usan @DataJpaTest
 * 
 * ===============================================
 * EJECUTAR ESTOS TESTS:
 * ===============================================
 * 
 * mvn test -Dtest=SocioRepositoryTest
 * 
 * RESULTADO ESPERADO: 6 tests pasando ✅
 * ===============================================
 */

