# 📚 DOCUMENTACIÓN TÉCNICA - Gross Gym Fitness

## Índice

1. [Arquitectura del Sistema](#arquitectura-del-sistema)
2. [Modelo de Datos](#modelo-de-datos)
3. [Scripts SQL](#scripts-sql)
4. [Capa de Persistencia](#capa-de-persistencia)
5. [Capa de Negocio](#capa-de-negocio)
6. [Capa de Controlador](#capa-de-controlador)
7. [Frontend Angular](#frontend-angular)
8. [Flujo de Funcionamiento](#flujo-de-funcionamiento)
9. [Casos de Uso](#casos-de-uso)
10. [Seguridad y Validaciones](#seguridad-y-validaciones)

---

## 1. Arquitectura del Sistema

### 1.1 Patrón Arquitectónico

El sistema implementa una **Arquitectura en Capas (Layered Architecture)** con el patrón **MVC (Model-View-Controller)**:

```
Frontend (Angular)
    ↓ HTTP/REST
Backend (Spring Boot)
    ├── Capa de Presentación (Controllers)
    ├── Capa de Negocio (Services)
    ├── Capa de Persistencia (Repositories)
    └── Capa de Datos (Entities)
    ↓ JDBC
Base de Datos (MySQL)
```

### 1.2 Tecnologías por Capa

| Capa | Tecnología | Responsabilidad |
|------|------------|-----------------|
| **Presentación** | Angular 18+, TypeScript, Bootstrap 5 | UI/UX, Interacción usuario |
| **Controlador** | Spring MVC (@RestController) | API REST, Routing, Validación entrada |
| **Negocio** | Spring Service (@Service) | Lógica de negocio, Reglas, Cálculos |
| **Persistencia** | Spring Data JPA (@Repository) | Acceso a datos, Queries |
| **Datos** | MySQL 8.0 | Almacenamiento persistente |

---

## 2. Modelo de Datos

### 2.1 Diagrama Entidad-Relación

```
                                ┌─────────────┐
                                │  TipoPago   │
                                │─────────────│
                                │ id_pago (PK)│
                                │ descripcion │
                                │ estado      │
                                └──────┬──────┘
                                       │
                                       │
┌─────────────┐         ┌─────────────▼──────┐         ┌─────────────┐
│   Socio     │         │   Suscripcion      │         │    Plan     │
│─────────────│         │────────────────────│         │─────────────│
│ rut (PK)    │◄────────│ id_suscripcion(PK) │────────►│ id_plan(PK) │
│ nombres     │         │ fecha_creacion     │         │ tipo_plan   │
│ apellido_p  │         │ nro_transaccion    │         │ monto_plan  │
│ apellido_m  │         │ id_tipo_pago (FK)  │         │ duracion    │
│ genero      │         │ id_plan (FK)       │         │ unidad      │
│ correo      │         │ monto_plan         │         │ isMatricula │
│ celular     │         │ monto_matricula    │         │ monto_mat   │
│ habilitado  │         │ nro_cuotas         │         └─────────────┘
└──────┬──────┘         │ fecha_termino      │
       │                │ id_estado (FK)     │
       │                │ id_socio (FK)      │
       │                └────────┬───────────┘
       │                         │
       │                         │
       │                ┌────────▼───────┐
       │                │    Estado      │
       │                │────────────────│
       │                │ id_estado (PK) │
       │                │ descripcion    │
       │                │ habilitado     │
       │                └────────────────┘
       │
       │                ┌─────────────────┐
       └───────────────►│    Inbody       │◄────────┐
                        │─────────────────│         │
                        │ id_inbody (PK)  │         │
                        │ id_socio (FK)   │         │
                        │ habilitado      │   ┌─────┴─────────┐
                        │ json_inbody     │   │  Instructor   │
                        │ fecha_eval      │   │───────────────│
                        │ comentario      │   │ id_instr (PK) │
                        │ id_instructor(FK)│──►│ nombre_instr  │
                        └─────────────────┘   │ habilitado    │
                                              └───────────────┘
```

### 2.2 Descripción de Entidades

#### Socio
- **Propósito**: Representa a los miembros del gimnasio
- **Clave Primaria**: `rut` (String)
- **Atributos Principales**: nombres, apellidos, género, correo, celular, estado

#### Suscripción
- **Propósito**: Registro de membresías de los socios
- **Clave Primaria**: `id_suscripcion` (Integer, auto-increment)
- **Relaciones**:
  - `ManyToOne` con Socio
  - `ManyToOne` con Plan
  - `ManyToOne` con TipoPago
  - `ManyToOne` con Estado

#### Plan
- **Propósito**: Tipos de membresías disponibles (Diario, Mensual, Anual)
- **Clave Primaria**: `id_plan` (Integer, auto-increment)
- **Atributos Clave**: tipo, monto, duración, unidad, matrícula

#### TipoPago
- **Propósito**: Métodos de pago aceptados (Efectivo, Débito, Crédito, Transferencia)
- **Clave Primaria**: `id_pago` (Integer, auto-increment)

#### Estado
- **Propósito**: Estados de las suscripciones (Activo, Vencido, Suspendido, Cancelado)
- **Clave Primaria**: `id_estado` (Integer, auto-increment)

#### Instructor
- **Propósito**: Profesionales que realizan evaluaciones
- **Clave Primaria**: `id_instructor` (Integer, auto-increment)

#### Inbody
- **Propósito**: Evaluaciones de composición corporal
- **Clave Primaria**: `id_inbody` (Long, auto-increment)
- **Atributo Especial**: `json_inbody` (almacena datos de evaluación en formato JSON)

---

## 3. Scripts SQL

### 3.1 Creación de Base de Datos

```sql
CREATE DATABASE db_grossgym_fitness 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_general_ci;

USE db_grossgym_fitness;
```

### 3.2 Tablas (Ver scripts originales en el proyecto)

Las tablas se crean automáticamente con Hibernate DDL auto-update, pero para producción se recomienda usar los scripts SQL proporcionados en el proyecto.

### 3.3 Datos Iniciales (Seed Data)

```sql
-- Tipos de Pago
INSERT INTO tipo_pago (descripcion, estado) VALUES
('Efectivo', true),
('Tarjeta de Débito', true),
('Tarjeta de Crédito', true),
('Transferencia Bancaria', true),
('WebPay', true);

-- Planes
INSERT INTO plan (tipo_plan, monto_plan, duracion, unidad, isMatricula, monto_matricula) VALUES
('Plan Diario', 5000, 1, 'DIA', false, 0),
('Plan Semanal', 25000, 7, 'DIA', false, 0),
('Plan Mensual', 35000, 1, 'MES', true, 15000),
('Plan Trimestral', 90000, 3, 'MES', true, 20000),
('Plan Semestral', 160000, 6, 'MES', true, 25000),
('Plan Anual', 300000, 1, 'AÑO', true, 30000);

-- Estados
INSERT INTO estado (descripcion, habilitado) VALUES
('Activo', true),
('Vencido', true),
('Suspendido', true),
('Cancelado', true),
('Pendiente de Pago', true);

-- Instructores
INSERT INTO instructor (nombre_instructor, habilitado) VALUES
('Carlos Pérez', true),
('María González', true),
('Luis Rodríguez', true);
```

---

## 4. Capa de Persistencia

### 4.1 Repositorios (Spring Data JPA)

#### Ejemplo: SocioRepository

```java
@Repository
public interface SocioRepository extends JpaRepository<Socio, String> {
    List<Socio> findByHabilitado(Boolean habilitado);
    Socio findByCorreo(String correo);
    List<Socio> findByNombresContaining(String nombres);
}
```

**Características**:
- Extiende `JpaRepository<T, ID>`
- Métodos CRUD automáticos
- Query methods por convención de nombres
- `@Query` para consultas personalizadas

### 4.2 Queries Personalizadas

```java
@Query("SELECT s FROM Suscripcion s WHERE s.fechaTermino > ?1")
List<Suscripcion> findSuscripcionesVigentes(LocalDateTime fecha);
```

---

## 5. Capa de Negocio

### 5.1 Servicios

#### Patrón: Interface + Implementación

```java
// Interface
public interface SocioService {
    List<Socio> findAll();
    Optional<Socio> findById(String rut);
    Socio save(Socio socio);
    void deleteById(String rut);
}

// Implementación
@Service
@Transactional
public class SocioServiceImpl implements SocioService {
    private final SocioRepository socioRepository;
    
    public SocioServiceImpl(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }
    
    @Override
    public Socio save(Socio socio) {
        // Validaciones de negocio
        if (socio.getRut() == null || socio.getRut().trim().isEmpty()) {
            throw new IllegalArgumentException("El RUT no puede estar vacío");
        }
        return socioRepository.save(socio);
    }
}
```

### 5.2 Lógica de Negocio Importante

#### Cálculo de Fecha de Término de Suscripción

```java
private LocalDateTime calcularFechaTermino(LocalDateTime fechaInicio, 
                                          Integer duracion, 
                                          String unidad) {
    return switch (unidad.toUpperCase()) {
        case "DIA" -> fechaInicio.plusDays(duracion);
        case "MES" -> fechaInicio.plusMonths(duracion);
        case "AÑO", "ANIO" -> fechaInicio.plusYears(duracion);
        default -> fechaInicio.plusMonths(duracion);
    };
}
```

**Ventaja Java 21**: Uso de `switch expressions` mejoradas.

---

## 6. Capa de Controlador

### 6.1 REST Controllers

#### Ejemplo: SocioController

```java
@RestController
@RequestMapping("/socios")
@CrossOrigin(origins = "*")
public class SocioController {
    
    private final SocioService socioService;
    
    @GetMapping
    public ResponseEntity<List<Socio>> getAllSocios() {
        return ResponseEntity.ok(socioService.findAll());
    }
    
    @PostMapping
    public ResponseEntity<Socio> createSocio(@RequestBody Socio socio) {
        Socio nuevoSocio = socioService.save(socio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoSocio);
    }
    
    @PutMapping("/{rut}")
    public ResponseEntity<Socio> updateSocio(@PathVariable String rut, 
                                            @RequestBody Socio socio) {
        return socioService.findById(rut)
                .map(s -> {
                    socio.setRut(rut);
                    return ResponseEntity.ok(socioService.save(socio));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
```

### 6.2 Respuestas HTTP

| Código | Significado | Uso |
|--------|-------------|-----|
| 200 OK | Éxito | GET, PUT exitoso |
| 201 Created | Recurso creado | POST exitoso |
| 204 No Content | Sin contenido | DELETE exitoso |
| 400 Bad Request | Datos inválidos | Validación fallida |
| 404 Not Found | No encontrado | Recurso inexistente |
| 500 Internal Error | Error servidor | Excepción no manejada |

---

## 7. Frontend Angular

### 7.1 Estructura de Componentes

```
components/
├── socios/
│   ├── socios-list/          # Lista de socios
│   └── socios-form/          # Formulario crear/editar
├── suscripciones/
│   ├── suscripciones-list/   # Lista de suscripciones
│   └── suscripciones-form/   # Formulario crear/editar
└── planes/
    └── planes-list/          # Vista de planes
```

### 7.2 Servicios HTTP

```typescript
@Injectable({
  providedIn: 'root'
})
export class SocioService {
  private apiUrl = 'http://localhost:8080/api/socios';
  
  constructor(private http: HttpClient) {}
  
  getAll(): Observable<Socio[]> {
    return this.http.get<Socio[]>(this.apiUrl);
  }
  
  create(socio: Socio): Observable<Socio> {
    return this.http.post<Socio>(this.apiUrl, socio);
  }
}
```

### 7.3 Componentes Standalone (Angular 18+)

```typescript
@Component({
  selector: 'app-socios-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `...`,
  styles: [`...`]
})
export class SociosListComponent implements OnInit {
  // Lógica del componente
}
```

**Ventaja**: No se requieren NgModules, componentes más modulares.

---

## 8. Flujo de Funcionamiento

### 8.1 Flujo de Creación de Suscripción

```
1. Usuario accede a /suscripciones/nuevo
   ↓
2. Angular carga SuscripcionesFormComponent
   ↓
3. Componente carga catálogos:
   - GET /api/socios (socios habilitados)
   - GET /api/planes (todos los planes)
   - GET /api/tipos-pago/activos (tipos de pago)
   ↓
4. Usuario selecciona datos y envía formulario
   ↓
5. POST /api/suscripciones con JSON:
   {
     "socio": { "rut": "12345678-9" },
     "plan": { "idPlan": 3 },
     "tipoPago": { "idPago": 1 },
     "nroTransaccion": "TRX-12345",
     "montoPlan": 35000,
     "montoMatricula": 15000
   }
   ↓
6. SuscripcionController recibe request
   ↓
7. SuscripcionService valida y procesa:
   - Valida datos obligatorios
   - Establece fechaCreacion = now()
   - Calcula fechaTermino según plan
   ↓
8. SuscripcionRepository guarda en BD
   ↓
9. Response 201 Created con suscripción creada
   ↓
10. Angular navega a /suscripciones (lista)
```

### 8.2 Flujo de Consulta de Socios

```
Usuario → Angular → GET /api/socios → Controller → Service → Repository → MySQL
                                                                              ↓
Usuario ← Angular ← 200 OK + JSON ← Controller ← Service ← Repository ← Resultados
```

---

## 9. Casos de Uso

### 9.1 Caso de Uso: Registrar Nuevo Socio

**Actor**: Recepcionista

**Precondiciones**: Sistema en funcionamiento

**Flujo Principal**:
1. Usuario accede al módulo de Socios
2. Hace clic en "Nuevo Socio"
3. Completa formulario:
   - RUT (único, obligatorio)
   - Nombres y apellidos (obligatorios)
   - Género (obligatorio)
   - Correo (obligatorio, válido)
   - Celular (obligatorio)
   - Estado habilitado (checkbox)
4. Hace clic en "Guardar"
5. Sistema valida datos
6. Sistema crea socio en BD
7. Sistema muestra mensaje de éxito
8. Sistema redirige a lista de socios

**Flujo Alternativo (Error)**:
- 5a. Datos inválidos → mostrar mensaje de error
- 6a. RUT duplicado → mostrar "RUT ya existe"

### 9.2 Caso de Uso: Crear Suscripción

**Actor**: Recepcionista/Vendedor

**Precondiciones**: 
- Socio registrado
- Planes configurados

**Flujo Principal**:
1. Usuario accede a "Nueva Suscripción"
2. Selecciona socio del dropdown
3. Selecciona plan (se cargan montos automáticamente)
4. Selecciona tipo de pago
5. Ingresa número de transacción
6. (Opcional) Ingresa número de cuotas
7. Revisa monto total
8. Hace clic en "Guardar"
9. Sistema crea suscripción
10. Sistema calcula fecha de término automáticamente
11. Sistema establece estado como "Activo"
12. Sistema muestra mensaje de éxito

---

## 10. Seguridad y Validaciones

### 10.1 Validaciones Backend

```java
@Override
public Suscripcion save(Suscripcion suscripcion) {
    // Validaciones de negocio
    if (suscripcion.getSocio() == null) {
        throw new IllegalArgumentException("Suscripción debe tener socio");
    }
    if (suscripcion.getPlan() == null) {
        throw new IllegalArgumentException("Suscripción debe tener plan");
    }
    // ... más validaciones
}
```

### 10.2 Validaciones Frontend

```typescript
<form (ngSubmit)="guardarSocio()" #socioForm="ngForm">
  <input type="text" 
         name="rut" 
         [(ngModel)]="socio.rut" 
         required 
         pattern="[0-9]{7,8}-[0-9Kk]">
  <input type="email" 
         name="correo" 
         [(ngModel)]="socio.correo" 
         required 
         email>
</form>
```

### 10.3 CORS Configuration

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
```

### 10.4 Transacciones

```java
@Service
@Transactional  // Todas las operaciones son transaccionales
public class SuscripcionServiceImpl implements SuscripcionService {
    
    @Transactional(readOnly = true)  // Optimización para consultas
    public List<Suscripcion> findAll() {
        return suscripcionRepository.findAll();
    }
}
```

---

## 11. Mejoras y Extensiones Futuras

### 11.1 Funcionalidades Pendientes

- [ ] Autenticación y autorización (Spring Security + JWT)
- [ ] Reportes en PDF (JasperReports)
- [ ] Dashboard con estadísticas
- [ ] Notificaciones por correo (próximas a vencer)
- [ ] Pagos online integrados
- [ ] Control de acceso al gimnasio (QR codes)
- [ ] App móvil nativa

### 11.2 Optimizaciones Técnicas

- [ ] Caché con Redis
- [ ] Búsqueda con Elasticsearch
- [ ] Logs centralizados (ELK Stack)
- [ ] Métricas con Prometheus + Grafana
- [ ] Tests de integración completos
- [ ] CI/CD con GitHub Actions

---

## 12. Comandos Útiles

### Backend

```bash
# Compilar proyecto
mvn clean compile

# Ejecutar tests
mvn test

# Empaquetar JAR
mvn clean package

# Ejecutar aplicación
mvn spring-boot:run

# Generar documentación
mvn javadoc:javadoc
```

### Frontend

```bash
# Instalar dependencias
npm install

# Servidor de desarrollo
ng serve

# Build producción
ng build --configuration production

# Tests unitarios
ng test

# Análisis de código
ng lint
```

### Docker

```bash
# Construir imagen
docker build -t grossgym-backend .

# Ejecutar contenedor
docker run -p 8080:8080 grossgym-backend

# Docker Compose
docker-compose up -d
docker-compose logs -f
docker-compose down
```

---

## 13. Troubleshooting

### Problema: Error de conexión a MySQL

**Solución**:
```bash
# Verificar que MySQL esté corriendo
sudo systemctl status mysql

# Verificar conexión
mysql -u root -p

# Verificar puerto
netstat -an | grep 3306
```

### Problema: CORS en producción

**Solución**: Configurar `cors.allowed-origins` en `application.properties`

### Problema: Frontend no conecta con backend

**Solución**: Verificar URL en services (`http://localhost:8080/api`)

---

**Fin de la Documentación Técnica**

Para más información, consultar el código fuente o contactar al equipo de desarrollo.

