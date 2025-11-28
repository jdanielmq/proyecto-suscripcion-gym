# 📚 Guía de Aprendizaje: Testing en Spring Boot

## 📖 Índice

1. [Introducción](#introducción)
2. [Tipos de Tests](#tipos-de-tests)
3. [Herramientas Usadas](#herramientas-usadas)
4. [Tests de Repository](#tests-de-repository)
5. [Tests de Service](#tests-de-service)
6. [Tests de Controller](#tests-de-controller)
7. [Tests de Model](#tests-de-model)
8. [Tests de Config](#tests-de-config)
9. [Mocks vs. Tests Reales](#mocks-vs-tests-reales)
10. [Cobertura de Código](#cobertura-de-código)
11. [Buenas Prácticas](#buenas-prácticas)

---

## 🎯 Introducción

Este documento te enseña **cómo crear tests** en Spring Boot desde cero.

### ¿Por qué testear?

- ✅ **Confianza**: Sabes que tu código funciona
- ✅ **Refactorización segura**: Puedes cambiar código sin romper nada
- ✅ **Documentación viva**: Los tests muestran cómo usar tu código
- ✅ **Menos bugs**: Detectas errores antes de producción

---

## 🧩 Tipos de Tests

### 1. **Unit Tests (Tests Unitarios)**
- Testean **una sola clase** aislada
- Usan **mocks** para las dependencias
- Son **rápidos**
- Ejemplo: Testear `SocioService` sin conectar a BD

### 2. **Integration Tests (Tests de Integración)**
- Testean **varias capas** juntas
- Usan **base de datos real** (o H2)
- Son **más lentos**
- Ejemplo: Testear controller + service + repository

### 3. **End-to-End Tests (Tests E2E)**
- Testean **toda la aplicación**
- Incluyen **frontend + backend**
- Son **muy lentos**
- Ejemplo: Selenium abriendo el navegador

### ¿Cuál usar?

```
Unit Tests      → 70% (más simples y rápidos)
Integration     → 20% (casos críticos)
E2E             → 10% (flujos completos)
```

---

## 🛠️ Herramientas Usadas

### 1. **JUnit 5** (Framework de Testing)
```java
@Test
void testAlgo() {
    // Tu test aquí
}
```

### 2. **Mockito** (Para crear Mocks)
```java
@Mock
private SocioRepository repository;

when(repository.findById("123")).thenReturn(Optional.of(socio));
```

### 3. **AssertJ** (Verificaciones)
```java
assertThat(resultado).isNotNull();
assertThat(resultado.getNombre()).isEqualTo("Juan");
```

### 4. **MockMvc** (Para testear Controllers)
```java
mockMvc.perform(get("/api/socios"))
    .andExpect(status().isOk());
```

### 5. **JaCoCo** (Cobertura de Código)
```bash
mvn test
# Genera reporte en: target/site/jacoco/index.html
```

---

## 🗄️ Tests de Repository

### ¿Qué es un Repository?

Es la **capa que interactúa con la base de datos**.

```java
public interface SocioRepository extends JpaRepository<Socio, String> {
    List<Socio> findByHabilitado(Boolean habilitado);
    List<Socio> findByNombresContaining(String nombres);
}
```

### Estructura de un Test de Repository

```java
@ExtendWith(MockitoExtension.class)
class SocioRepositoryTest {
    
    @Mock
    private SocioRepository repository;
    
    @Test
    void testSave() {
        // 1. PREPARAR (Arrange)
        Socio socio = new Socio();
        socio.setRut("12345678-9");
        
        // 2. CONFIGURAR MOCK (Mock)
        when(repository.save(any(Socio.class))).thenReturn(socio);
        
        // 3. EJECUTAR (Act)
        Socio resultado = repository.save(socio);
        
        // 4. VERIFICAR (Assert)
        assertThat(resultado).isNotNull();
        assertThat(resultado.getRut()).isEqualTo("12345678-9");
        
        // 5. VERIFICAR LLAMADA (Verify)
        verify(repository).save(any(Socio.class));
    }
}
```

### Archivos de Ejemplo

- ✅ `src/test/java/com/grossgym/fitness/repository/SocioRepositoryTest.java`
- ✅ `src/test/java/com/grossgym/fitness/repository/PlanRepositoryTest.java`
- ✅ `src/test/java/com/grossgym/fitness/repository/TipoPagoRepositoryTest.java`

---

## 🏢 Tests de Service

### ¿Qué es un Service?

Es la **capa de lógica de negocio**.

```java
@Service
public class SocioServiceImpl implements SocioService {
    
    @Autowired
    private SocioRepository repository;
    
    public Socio save(Socio socio) {
        // Validaciones
        if (socio.getRut() == null) {
            throw new IllegalArgumentException("RUT requerido");
        }
        return repository.save(socio);
    }
}
```

### Estructura de un Test de Service

```java
@ExtendWith(MockitoExtension.class)
class SocioServiceImplTest {
    
    @Mock
    private SocioRepository repository;  // Mockear el repository
    
    @InjectMocks
    private SocioServiceImpl service;  // Inyectar mocks en el service
    
    @Test
    void testSaveConRutNulo() {
        // Preparar
        Socio socio = new Socio();
        socio.setRut(null);  // RUT nulo
        
        // Ejecutar y verificar excepción
        assertThatThrownBy(() -> service.save(socio))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("RUT requerido");
        
        // Verificar que NO se llamó al repository
        verify(repository, never()).save(any());
    }
    
    @Test
    void testSaveExitoso() {
        // Preparar
        Socio socio = new Socio();
        socio.setRut("12345678-9");
        
        // Configurar mock
        when(repository.save(any(Socio.class))).thenReturn(socio);
        
        // Ejecutar
        Socio resultado = service.save(socio);
        
        // Verificar
        assertThat(resultado).isNotNull();
        verify(repository).save(socio);
    }
}
```

### Archivos de Ejemplo

- ✅ `src/test/java/com/grossgym/fitness/service/impl/SocioServiceImplTest.java`
- ✅ `src/test/java/com/grossgym/fitness/service/impl/PlanServiceImplTest.java`
- ✅ `src/test/java/com/grossgym/fitness/service/impl/SuscripcionServiceImplTest.java`

---

## 🎮 Tests de Controller

### ¿Qué es un Controller?

Es la **capa que recibe peticiones HTTP**.

```java
@RestController
@RequestMapping("/api/socios")
public class SocioController {
    
    @Autowired
    private SocioService service;
    
    @GetMapping
    public List<Socio> findAll() {
        return service.findAll();
    }
    
    @GetMapping("/{rut}")
    public ResponseEntity<Socio> findById(@PathVariable String rut) {
        return service.findById(rut)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
```

### Estructura de un Test de Controller

```java
@WebMvcTest(SocioController.class)
class SocioControllerTest {
    
    @Autowired
    private MockMvc mockMvc;  // Simula peticiones HTTP
    
    @MockBean
    private SocioService service;  // Mockea el servicio
    
    @Test
    void testGetAllSocios() throws Exception {
        // Preparar
        Socio socio1 = new Socio();
        socio1.setRut("123");
        Socio socio2 = new Socio();
        socio2.setRut("456");
        
        // Configurar mock
        when(service.findAll()).thenReturn(Arrays.asList(socio1, socio2));
        
        // Ejecutar y verificar
        mockMvc.perform(get("/api/socios"))
            .andExpect(status().isOk())  // 200
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].rut").value("123"))
            .andExpect(jsonPath("$[1].rut").value("456"));
    }
    
    @Test
    void testGetSocioNotFound() throws Exception {
        // Configurar: retornar vacío
        when(service.findById("999")).thenReturn(Optional.empty());
        
        // Ejecutar y verificar
        mockMvc.perform(get("/api/socios/999"))
            .andExpect(status().isNotFound());  // 404
    }
}
```

### Archivos de Ejemplo

- ✅ `src/test/java/com/grossgym/fitness/controller/SocioControllerTest.java`
- ✅ `src/test/java/com/grossgym/fitness/controller/PlanControllerTest.java`
- 📖 `src/test/java/com/grossgym/fitness/controller/SocioControllerEducationalTest.java` **(COMENTADO)**

---

## 📦 Tests de Model

### ¿Qué es un Model?

Son las **entidades JPA** (POJOs).

```java
@Entity
@Table(name = "socio")
public class Socio {
    @Id
    private String rut;
    private String nombres;
    // ... getters y setters
}
```

### Estructura de un Test de Model

```java
class SocioTest {
    
    @Test
    void testConstructorYGetters() {
        // Crear
        Socio socio = new Socio();
        socio.setRut("12345678-9");
        socio.setNombres("Juan");
        
        // Verificar
        assertThat(socio.getRut()).isEqualTo("12345678-9");
        assertThat(socio.getNombres()).isEqualTo("Juan");
    }
    
    @Test
    void testEquals() {
        Socio socio1 = new Socio();
        socio1.setRut("123");
        
        Socio socio2 = new Socio();
        socio2.setRut("123");
        
        // Mismo RUT = iguales
        assertThat(socio1).isEqualTo(socio2);
    }
}
```

### Archivos de Ejemplo

- ✅ `src/test/java/com/grossgym/fitness/model/SocioTest.java`
- ✅ `src/test/java/com/grossgym/fitness/model/PlanTest.java`

---

## ⚙️ Tests de Config

### ¿Qué es una Config?

Son las **clases de configuración** de Spring.

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOriginPatterns("http://localhost:4200")
            .allowedMethods("*");
    }
}
```

### Estructura de un Test de Config

```java
@ExtendWith(MockitoExtension.class)
class CorsConfigTest {
    
    @Test
    void testCorsConfigImplementaWebMvcConfigurer() {
        CorsConfig config = new CorsConfig();
        
        assertThat(config)
            .isInstanceOf(WebMvcConfigurer.class);
    }
}
```

### Archivos de Ejemplo

- ✅ `src/test/java/com/grossgym/fitness/config/CorsConfigTest.java`

---

## 🎭 Mocks vs. Tests Reales

### ¿Qué es un Mock?

Un **Mock** es un objeto **falso** que simula el comportamiento de un objeto real.

### ¿Cuándo usar Mocks?

```
✅ USAR MOCKS:
- Unit tests (testear una clase sola)
- Evitar dependencias externas (BD, APIs)
- Tests rápidos

❌ NO USAR MOCKS:
- Integration tests (testear varias capas)
- Testear que la BD funciona
- Testear queries SQL complejas
```

### Ejemplo Comparativo

#### Con Mock (Unit Test)
```java
@Mock
private SocioRepository repository;

@Test
void testFindById() {
    when(repository.findById("123")).thenReturn(Optional.of(socio));
    // NO conecta a BD
}
```

#### Sin Mock (Integration Test)
```java
@SpringBootTest
@AutoConfigureTestDatabase
class SocioRepositoryIntegrationTest {
    
    @Autowired
    private SocioRepository repository;
    
    @Test
    void testFindById() {
        Socio socio = repository.findById("123");
        // SÍ conecta a BD (H2 en memoria)
    }
}
```

---

## 📊 Cobertura de Código

### ¿Qué es la cobertura?

Es el **porcentaje de código** que está cubierto por tests.

### Ver Cobertura con JaCoCo

```bash
# Ejecutar tests y generar reporte
mvn clean test

# Ver reporte en navegador
open target/site/jacoco/index.html
```

### Interpretar el Reporte

```
Verde  = Cubierto por tests ✅
Rojo   = NO cubierto ❌
Amarillo = Parcialmente cubierto ⚠️
```

### Metas de Cobertura

```
Service Layer    → 80-90% (lógica de negocio)
Repository Layer → 60-70% (queries simples)
Controller Layer → 70-80% (endpoints)
Model Layer      → 50-60% (POJOs simples)
```

---

## ✅ Buenas Prácticas

### 1. **Naming (Nombres)**

```java
// ❌ MAL
@Test
void test1() { }

// ✅ BIEN
@Test
@DisplayName("save() debe guardar un socio exitosamente")
void testSaveGuardaSocioExitosamente() { }
```

### 2. **Arrange-Act-Assert (AAA)**

```java
@Test
void testSave() {
    // ARRANGE (Preparar)
    Socio socio = new Socio();
    socio.setRut("123");
    
    // ACT (Ejecutar)
    Socio resultado = service.save(socio);
    
    // ASSERT (Verificar)
    assertThat(resultado).isNotNull();
}
```

### 3. **Un Test = Una Cosa**

```java
// ❌ MAL - testea 2 cosas
@Test
void testSaveYDelete() {
    service.save(socio);
    service.delete(socio.getRut());
}

// ✅ BIEN - 1 test por cada cosa
@Test
void testSave() { ... }

@Test
void testDelete() { ... }
```

### 4. **Tests Independientes**

```java
// Cada test debe poder ejecutarse solo
// NO depender del orden de ejecución
// NO compartir estado entre tests

@BeforeEach
void setUp() {
    // Preparar datos limpios para cada test
}
```

### 5. **Nombres Descriptivos**

```java
// ✅ BIEN
void testSaveConRutNuloDebeLanzarExcepcion()
void testFindByIdConIdInexistenteDebeRetornarVacio()
void testDeleteConIdValidoDebeEliminar()
```

---

## 🎓 Ejercicios Prácticos

### Ejercicio 1: Crear Test de Repository

Crea un test para `EstadoRepository`:

```java
@Test
void testFindByHabilitado() {
    // Tu código aquí
}
```

### Ejercicio 2: Crear Test de Service con Validación

Crea un test que verifique que `PlanService` no permite guardar un plan con monto negativo.

### Ejercicio 3: Crear Test de Controller

Crea un test para el endpoint `POST /api/planes` que verifique el código HTTP 201.

---

## 📚 Recursos Adicionales

- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [AssertJ Documentation](https://assertj.github.io/doc/)
- [Spring Testing Documentation](https://docs.spring.io/spring-framework/reference/testing.html)

---

## 🎉 Conclusión

¡Ahora sabes cómo crear tests en Spring Boot!

**Recuerda:**
- 🎯 Los tests dan **confianza**
- 🚀 Los tests permiten **refactorizar** sin miedo
- 📖 Los tests son **documentación viva**
- ✅ Los tests **reducen bugs**

**Próximos pasos:**
1. Revisa los tests existentes en `src/test/`
2. Ejecuta `mvn test` y ve el resultado
3. Abre el reporte de JaCoCo
4. Intenta crear tu propio test

---

**Happy Testing!** 🧪✨

