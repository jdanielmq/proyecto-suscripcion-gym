# 🧪 Testing y Logging - Gross Gym Fitness

> **Tests Unitarios con JUnit 5 + Mockito**  
> **Logging Empresarial con SLF4J + Logback**

---

## 📋 TABLA DE CONTENIDOS

1. [Tests Unitarios](#tests-unitarios)
2. [Logging Empresarial](#logging-empresarial)
3. [Ejecutar Tests](#ejecutar-tests)
4. [Cobertura de Código](#cobertura-de-código)
5. [Mejores Prácticas](#mejores-prácticas)

---

## 🧪 TESTS UNITARIOS

### Tecnologías

| Librería | Versión | Propósito |
|----------|---------|-----------|
| **JUnit 5** | 5.10.1 | Framework de testing |
| **Mockito** | 5.7.0 | Mocking framework |
| **AssertJ** | 3.24.2 | Assertions fluidas |
| **Spring Boot Test** | 3.2.0 | Testing de Spring |
| **MockMvc** | 6.1.0 | Testing de controllers |

### Estructura de Tests

```
src/test/java/
└── com/grossgym/fitness/
    ├── service/impl/
    │   ├── SocioServiceImplTest.java       ← Tests de servicios
    │   ├── PlanServiceImplTest.java
    │   └── SuscripcionServiceImplTest.java
    └── controller/
        ├── SocioControllerTest.java         ← Tests de controllers
        ├── PlanControllerTest.java
        └── SuscripcionControllerTest.java
```

---

## 📝 TIPOS DE TESTS

### 1. Tests de Servicios (Unit Tests)

**Qué se prueba:**
- Lógica de negocio
- Interacción con repositories
- Validaciones
- Manejo de errores

**Ejemplo: SocioServiceImplTest.java**

```java
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Servicio de Socios")
class SocioServiceImplTest {

    @Mock
    private SocioRepository socioRepository;

    @InjectMocks
    private SocioServiceImpl socioService;

    @Test
    @DisplayName("findAll() debe retornar lista de socios")
    void testFindAll_DebeRetornarListaDeSocios() {
        // Arrange
        List<Socio> socios = Arrays.asList(socio1, socio2);
        when(socioRepository.findAll()).thenReturn(socios);

        // Act
        List<Socio> resultado = socioService.findAll();

        // Assert
        assertThat(resultado)
            .isNotNull()
            .hasSize(2)
            .containsExactlyInAnyOrder(socio1, socio2);
        
        verify(socioRepository, times(1)).findAll();
    }
}
```

### 2. Tests de Controllers (Integration Tests)

**Qué se prueba:**
- Endpoints REST
- Request/Response HTTP
- Códigos de estado
- JSON serialization/deserialization

**Ejemplo: SocioControllerTest.java**

```java
@WebMvcTest(SocioController.class)
@DisplayName("Tests de Controller de Socios")
class SocioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SocioService socioService;

    @Test
    @DisplayName("GET /socios debe retornar lista con status 200")
    void testGetAllSocios_DebeRetornarListaConStatus200() throws Exception {
        // Arrange
        when(socioService.findAll()).thenReturn(socios);

        // Act & Assert
        mockMvc.perform(get("/socios")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].rut").value("12345678-9"));
    }
}
```

---

## 🔍 ANATOMÍA DE UN TEST

### Patrón AAA (Arrange-Act-Assert)

```java
@Test
@DisplayName("Descripción clara de lo que se prueba")
void testMetodo_CondicionEspecifica_ResultadoEsperado() {
    // ========================================
    // ARRANGE - Preparar
    // ========================================
    // Configurar datos de prueba
    Socio socio = new Socio();
    socio.setRut("12345678-9");
    
    // Configurar comportamiento de mocks
    when(repository.findById("12345678-9")).thenReturn(Optional.of(socio));

    // ========================================
    // ACT - Actuar
    // ========================================
    // Ejecutar el método a probar
    Optional<Socio> resultado = service.findById("12345678-9");

    // ========================================
    // ASSERT - Verificar
    // ========================================
    // Verificar el resultado
    assertThat(resultado)
        .isPresent()
        .contains(socio);
    
    // Verificar interacciones
    verify(repository, times(1)).findById("12345678-9");
}
```

---

## 🎯 ANOTACIONES IMPORTANTES

### JUnit 5

| Anotación | Propósito |
|-----------|-----------|
| `@Test` | Marca un método como test |
| `@BeforeEach` | Ejecuta antes de cada test |
| `@AfterEach` | Ejecuta después de cada test |
| `@DisplayName` | Nombre descriptivo del test |
| `@Disabled` | Desactiva un test temporalmente |
| `@ParameterizedTest` | Test parametrizado |

### Mockito

| Anotación | Propósito |
|-----------|-----------|
| `@Mock` | Crea un mock |
| `@InjectMocks` | Inyecta mocks en la clase |
| `@ExtendWith(MockitoExtension.class)` | Habilita Mockito |

### Spring Boot Test

| Anotación | Propósito |
|-----------|-----------|
| `@WebMvcTest` | Test de controllers (solo web layer) |
| `@SpringBootTest` | Test de integración completo |
| `@MockBean` | Mock de un bean de Spring |
| `@Autowired` | Inyección de dependencias |

---

## 📊 EJECUTAR TESTS

### Opción 1: Con Maven

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar tests con reporte
mvn clean test

# Ejecutar tests de una clase específica
mvn test -Dtest=SocioServiceImplTest

# Ejecutar un test específico
mvn test -Dtest=SocioServiceImplTest#testFindAll_DebeRetornarListaDeSocios

# Ejecutar tests sin compilar
mvn surefire:test

# Saltar tests (NO recomendado en producción)
mvn clean package -DskipTests
```

### Opción 2: Desde IDE

**IntelliJ IDEA:**
1. Click derecho en clase de test
2. "Run SocioServiceImplTest"
3. Ver resultados en panel inferior

**VS Code:**
1. Instalar extensión "Java Test Runner"
2. Click en icono de play junto al test
3. Ver resultados en panel de Testing

### Opción 3: Con Gradle (si aplica)

```bash
# Ejecutar todos los tests
./gradlew test

# Ver reporte HTML
./gradlew test --info
```

---

## 📈 SALIDA DE TESTS

### Ejemplo de Salida Exitosa

```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.grossgym.fitness.service.impl.SocioServiceImplTest
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.234 s
[INFO] Running com.grossgym.fitness.service.impl.PlanServiceImplTest
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.189 s
[INFO] Running com.grossgym.fitness.controller.SocioControllerTest
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.456 s
[INFO] Running com.grossgym.fitness.controller.SuscripcionControllerTest
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.234 s
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 35, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

### Ejemplo de Salida con Fallo

```
[ERROR] Failures: 
[ERROR]   SocioServiceImplTest.testFindAll_DebeRetornarListaDeSocios:45 
    Expected: collection containing [Socio(rut=12345678-9, ...), Socio(rut=98765432-1, ...)]
         but: was []
```

---

## 📊 COBERTURA DE CÓDIGO

### Con JaCoCo (Maven Plugin)

#### 1. Agregar Plugin al pom.xml

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.jacoco</groupId>
            <artifactId>jacoco-maven-plugin</artifactId>
            <version>0.8.11</version>
            <executions>
                <execution>
                    <goals>
                        <goal>prepare-agent</goal>
                    </goals>
                </execution>
                <execution>
                    <id>report</id>
                    <phase>test</phase>
                    <goals>
                        <goal>report</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

#### 2. Ejecutar Tests con Cobertura

```bash
# Generar reporte de cobertura
mvn clean test jacoco:report

# Abrir reporte HTML
open target/site/jacoco/index.html
```

#### 3. Ver Reporte

```
Cobertura de Código
├── Packages: 100%
├── Classes: 95%
├── Methods: 90%
├── Lines: 88%
└── Branches: 75%
```

---

## 📝 LOGGING EMPRESARIAL

### Tecnologías

| Librería | Versión | Propósito |
|----------|---------|-----------|
| **SLF4J** | 2.0.9 | API de logging |
| **Logback** | 1.4.14 | Implementación de logging |
| **Lombok** | Latest | `@Slf4j` annotation |

### Configuración

#### 1. Logback Configuration

**Archivo:** `src/main/resources/logback-spring.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- Appender para consola -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    
    <!-- Appender para archivo -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/grossgym-fitness.log</file>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <fileNamePattern>logs/grossgym-fitness.%d{yyyy-MM-dd}.%i.log.gz</fileNamePattern>
            <maxFileSize>10MB</maxFileSize>
            <maxHistory>30</maxHistory>
            <totalSizeCap>1GB</totalSizeCap>
        </rollingPolicy>
    </appender>
    
    <!-- Logger de la aplicación -->
    <logger name="com.grossgym.fitness" level="DEBUG"/>
    
    <!-- Logger raíz -->
    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="FILE"/>
    </root>
</configuration>
```

#### 2. Application Properties

**Archivo:** `src/main/resources/application.properties`

```properties
# Configuración de Logging
logging.level.root=INFO
logging.level.com.grossgym.fitness=DEBUG
logging.level.org.springframework.web=INFO
logging.level.org.hibernate.SQL=DEBUG

# Logging Pattern
logging.pattern.console=%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr([%thread]){magenta} %clr(%-5level){highlight} %clr(%logger{36}){cyan} %clr(-){faint} %msg%n
```

---

## 🎨 USO DE LOGGING

### Con Anotación @Slf4j (Recomendado)

```java
@Slf4j
@Service
public class SocioServiceImpl implements SocioService {
    
    @Override
    public List<Socio> findAll() {
        log.info("Obteniendo lista de todos los socios");
        
        List<Socio> socios = socioRepository.findAll();
        
        log.debug("Se encontraron {} socios", socios.size());
        
        return socios;
    }
    
    @Override
    public Socio save(Socio socio) {
        log.info("Guardando socio con RUT: {}", socio.getRut());
        
        try {
            Socio socioGuardado = socioRepository.save(socio);
            log.info("Socio guardado exitosamente: {}", socioGuardado.getRut());
            return socioGuardado;
        } catch (Exception e) {
            log.error("Error al guardar socio: {}", socio.getRut(), e);
            throw e;
        }
    }
}
```

### Sin Lombok (Alternativa)

```java
@Service
public class SocioServiceImpl implements SocioService {
    
    private static final Logger log = LoggerFactory.getLogger(SocioServiceImpl.class);
    
    @Override
    public List<Socio> findAll() {
        log.info("Obteniendo lista de todos los socios");
        // ...
    }
}
```

---

## 📊 NIVELES DE LOG

| Nivel | Uso | Ejemplo |
|-------|-----|---------|
| **TRACE** | Detalles más finos | `log.trace("Entrando al método con parámetro: {}", param)` |
| **DEBUG** | Información de debugging | `log.debug("Query ejecutada: {}", query)` |
| **INFO** | Información general | `log.info("Aplicación iniciada en puerto 8080")` |
| **WARN** | Advertencias | `log.warn("Pool de conexiones al 90%")` |
| **ERROR** | Errores | `log.error("Error al conectar a BD", exception)` |

---

## 🎯 MEJORES PRÁCTICAS

### Testing

#### ✅ SÍ Hacer

```java
// ✅ Nombres descriptivos
@Test
@DisplayName("findById() debe retornar socio cuando existe")
void testFindById_DebeRetornarSocioCuandoExiste() { }

// ✅ Un concepto por test
@Test
void testSave_DebeGuardarSocio() { }

@Test
void testSave_DebeLanzarExcepcionCuandoRutEsNull() { }

// ✅ Usar AssertJ para assertions fluidas
assertThat(resultado)
    .isNotNull()
    .hasSize(2);
```

#### ❌ NO Hacer

```java
// ❌ Nombres vagos
@Test
void test1() { }

// ❌ Múltiples conceptos en un test
@Test
void testSave() {
    // Prueba guardar
    // Prueba actualizar
    // Prueba eliminar
}

// ❌ Usar System.out.println en tests
@Test
void testSomething() {
    System.out.println("Debugging...");  // ❌ NO
}
```

### Logging

#### ✅ SÍ Hacer

```java
// ✅ Usar placeholders {} para parámetros
log.info("Usuario {} inició sesión a las {}", username, timestamp);

// ✅ Loggear excepciones con contexto
log.error("Error al procesar pago para socio {}", socioId, exception);

// ✅ Niveles apropiados
log.debug("Variable temporal: {}", temp);  // Solo en desarrollo
log.info("Pago procesado exitosamente");   // Información importante
log.error("Fallo crítico en sistema", ex); // Errores
```

#### ❌ NO Hacer

```java
// ❌ Concatenación de strings
log.info("Usuario " + username + " inició sesión");  // ❌ Ineficiente

// ❌ Loggear información sensible
log.info("Password: {}", password);  // ❌ NUNCA

// ❌ Usar System.out.println
System.out.println("Debug info");  // ❌ NO

// ❌ Logs en bucles sin control
for (int i = 0; i < 1000000; i++) {
    log.debug("Iteración {}", i);  // ❌ Llenará los logs
}
```

---

## 📁 ARCHIVOS DE LOG

### Ubicación

```
crud-suscripcion-gym/
└── logs/
    ├── grossgym-fitness.log              ← Log actual
    ├── grossgym-fitness-error.log        ← Solo errores
    ├── grossgym-fitness.2024-11-27.0.log.gz  ← Archivados
    └── grossgym-fitness.2024-11-26.0.log.gz
```

### Rotación de Logs

- **Tamaño máximo por archivo:** 10 MB
- **Historial:** 30 días
- **Tamaño total máximo:** 1 GB
- **Compresión:** Gzip automático

---

## 🔍 VER LOGS EN TIEMPO REAL

### Linux/macOS

```bash
# Ver logs en tiempo real
tail -f logs/grossgym-fitness.log

# Ver solo errores
tail -f logs/grossgym-fitness-error.log

# Ver logs con grep
tail -f logs/grossgym-fitness.log | grep ERROR

# Ver logs de un servicio específico
tail -f logs/grossgym-fitness.log | grep SocioService
```

### Windows (PowerShell)

```powershell
# Ver logs en tiempo real
Get-Content logs/grossgym-fitness.log -Wait -Tail 50
```

---

## ✅ CHECKLIST

### Tests

- [ ] Tests para todos los servicios
- [ ] Tests para todos los controllers
- [ ] Cobertura > 80%
- [ ] Todos los tests pasan
- [ ] Tests en CI/CD

### Logging

- [ ] Configuración de Logback
- [ ] @Slf4j en todas las clases
- [ ] Sin System.out.println
- [ ] Niveles de log apropiados
- [ ] Rotación de logs configurada

---

## 📚 RECURSOS

- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [AssertJ Documentation](https://assertj.github.io/doc/)
- [SLF4J Manual](http://www.slf4j.org/manual.html)
- [Logback Documentation](http://logback.qos.ch/documentation.html)

---

**✅ Tests y Logging Implementados! 🎉**

**Siguiente paso:** Ejecutar tests con `mvn test` y verificar logs

