# 🔧 Soluciones a Problemas Comunes

> Troubleshooting para **Gross Gym Fitness**  
> Backend (Spring Boot) + Frontend (Angular) + MySQL

---

## 📋 TABLA DE CONTENIDOS

1. [Problemas de Backend](#problemas-de-backend)
2. [Problemas de Frontend](#problemas-de-frontend)
3. [Problemas de Base de Datos](#problemas-de-base-de-datos)
4. [Problemas de CORS](#problemas-de-cors)
5. [Problemas de Docker](#problemas-de-docker)
6. [Problemas de Swagger UI](#problemas-de-swagger-ui)
7. [Problemas de Integración](#problemas-de-integración)

---

## 🔴 PROBLEMAS DE BACKEND

### Problema 1: Backend No Inicia

**Error:**
```
Failed to start bean 'dataSource'
Communications link failure
```

**Causas Comunes:**
1. MySQL no está corriendo
2. Credenciales incorrectas en `application.properties`
3. Base de datos no existe
4. Puerto 3306 bloqueado

**Solución:**

```bash
# 1. Verificar que MySQL esté corriendo
mysql -u jdanielmq -p
# Si falla → Iniciar MySQL

# 2. Verificar que la BD exista
mysql -u jdanielmq -p -e "SHOW DATABASES LIKE 'db_grossgym_fitness'"
# Si no existe → Crear BD
mysql -u jdanielmq -p < scripts/recreate-database.sql

# 3. Verificar credenciales en application.properties
cat src/main/resources/application.properties | grep datasource
# Debe ser:
# spring.datasource.username=jdanielmq
# spring.datasource.password=JdmQ1481

# 4. Verificar puerto
lsof -i :8080
# Si está ocupado → Cerrar proceso o cambiar puerto
```

---

### Problema 2: Puerto 8080 Ya en Uso

**Error:**
```
Web server failed to start. Port 8080 was already in use.
```

**Solución Rápida:**

```bash
# Ver qué está usando el puerto 8080
lsof -i :8080

# Matar el proceso
kill -9 [PID]

# O cambiar el puerto en application.properties
server.port=8081
```

---

### Problema 3: Error al Compilar con Maven

**Error:**
```
Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin
```

**Solución:**

```bash
# 1. Limpiar proyecto
mvn clean

# 2. Verificar versión de Java
java --version
# Debe ser Java 17

# 3. Verificar JAVA_HOME
echo $JAVA_HOME
# Debe apuntar a Java 17

# 4. Compilar de nuevo
mvn clean package -DskipTests

# 5. Si persiste, eliminar .m2 cache
rm -rf ~/.m2/repository
mvn clean install
```

---

### Problema 4: Hibernate/JPA Error

**Error:**
```
Unknown column 'socio0_.is_matricula' in 'field list'
```

**Causa:** Hibernate está convirtiendo `isMatricula` (Java) a `is_matricula` (SQL).

**Solución:**

```properties
# En application.properties, agregar:
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
```

---

### Problema 5: Foreign Key Constraint Fails

**Error:**
```
Referencing column 'id_instructor' and referenced column 'id_instructor' in foreign key constraint 'inbody_instructor_FK' are incompatible.
```

**Causa:** Tipos de datos diferentes entre FK y PK.

**Solución:**

```bash
# Recrear toda la base de datos con tipos compatibles
mysql -u jdanielmq -p db_grossgym_fitness < scripts/recreate-database.sql

# Verificar tipos
mysql -u jdanielmq -p db_grossgym_fitness -e "DESCRIBE instructor"
mysql -u jdanielmq -p db_grossgym_fitness -e "DESCRIBE inbody"
# Ambas deben tener 'id_instructor INT'
```

---

## 🎨 PROBLEMAS DE FRONTEND

### Problema 6: Frontend No Inicia

**Error:**
```
ng: command not found
```

**Solución:**

```bash
# Instalar Angular CLI globalmente
npm install -g @angular/cli@20

# Verificar instalación
ng version
```

---

### Problema 7: Error al Ejecutar `ng serve`

**Error:**
```
Module not found: Error: Can't resolve...
```

**Solución:**

```bash
cd frontend

# Limpiar y reinstalar dependencias
rm -rf node_modules package-lock.json
npm install

# Iniciar de nuevo
ng serve
```

---

### Problema 8: Puerto 4200 Ya en Uso

**Error:**
```
Port 4200 is already in use.
```

**Solución:**

```bash
# Opción 1: Cerrar el proceso
lsof -i :4200
kill -9 [PID]

# Opción 2: Usar otro puerto
ng serve --port 4201

# Actualizar CORS en backend para el nuevo puerto
# En CorsConfig.java agregar:
.allowedOriginPatterns("http://localhost:4201")
```

---

### Problema 9: Errores de TypeScript al Compilar

**Error:**
```
Property 'x' does not exist on type 'Y'
```

**Solución:**

```bash
# Verificar que los modelos estén correctos
ls frontend/src/app/models/

# Verificar imports en los componentes
# Ejemplo: socio.model.ts debe exportar interface Socio

# Si persiste, limpiar caché de TypeScript
cd frontend
rm -rf .angular node_modules
npm install
ng serve
```

---

### Problema 10: Página en Blanco (White Screen)

**Causas Comunes:**
1. Error de JavaScript en consola
2. Rutas mal configuradas
3. Backend no responde

**Solución:**

```bash
# 1. Abrir DevTools (F12) y ver la consola
# Buscar errores en rojo

# 2. Verificar que el backend esté corriendo
curl http://localhost:8080/api/planes

# 3. Verificar rutas en app.routes.ts
cat frontend/src/app/app.routes.ts

# 4. Limpiar caché del navegador
# Chrome: Ctrl+Shift+Del (Cmd+Shift+Del en Mac)

# 5. Reiniciar ng serve
cd frontend
ng serve
```

---

## 🗄️ PROBLEMAS DE BASE DE DATOS

### Problema 11: Access Denied for User

**Error:**
```
Access denied for user 'jdanielmq'@'localhost'
```

**Solución:**

```bash
# Conectarse como root
mysql -u root -p

# Crear usuario si no existe
CREATE USER 'jdanielmq'@'localhost' IDENTIFIED BY 'JdmQ1481';
GRANT ALL PRIVILEGES ON *.* TO 'jdanielmq'@'localhost';
FLUSH PRIVILEGES;
EXIT;

# Verificar
mysql -u jdanielmq -p
```

---

### Problema 12: Unknown Database

**Error:**
```
Unknown database 'db_grossgym_fitness'
```

**Solución:**

```bash
# Crear la base de datos
mysql -u jdanielmq -p -e "CREATE DATABASE IF NOT EXISTS db_grossgym_fitness;"

# Ejecutar script completo
mysql -u jdanielmq -p < scripts/recreate-database.sql

# Verificar
mysql -u jdanielmq -p -e "SHOW DATABASES"
```

---

### Problema 13: Table Doesn't Exist

**Error:**
```
Table 'db_grossgym_fitness.socio' doesn't exist
```

**Solución:**

```bash
# Crear todas las tablas
mysql -u jdanielmq -p db_grossgym_fitness < scripts/create-tables.sql

# Verificar
mysql -u jdanielmq -p db_grossgym_fitness -e "SHOW TABLES"

# Cargar datos iniciales
mysql -u jdanielmq -p db_grossgym_fitness < scripts/init.sql
```

---

### Problema 14: MySQL No Inicia

**macOS:**
```bash
# Verificar estado
brew services list

# Iniciar MySQL
brew services start mysql@8.0

# O manualmente
mysql.server start
```

**Linux:**
```bash
# Verificar estado
sudo systemctl status mysql

# Iniciar MySQL
sudo systemctl start mysql

# Habilitar en boot
sudo systemctl enable mysql
```

**Windows:**
```powershell
# Verificar estado
sc query MySQL80

# Iniciar MySQL
net start MySQL80
```

---

## 🌐 PROBLEMAS DE CORS

### Problema 15: CORS Error en el Navegador

**Error en Consola:**
```
Access to XMLHttpRequest at 'http://localhost:8080/api/socios' from origin 'http://localhost:4200' has been blocked by CORS policy
```

**Solución:**

```java
// 1. Verificar que CorsConfig.java tenga:
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:4200", "http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}

// 2. ELIMINAR @CrossOrigin de TODOS los controllers
// NO debe haber @CrossOrigin(origins = "*") en los controllers

// 3. Reiniciar backend
mvn spring-boot:run
```

---

### Problema 16: CORS con `allowCredentials`

**Error:**
```
When allowCredentials is true, allowedOrigins cannot contain the special value "*"
```

**Solución:**

```java
// Usar allowedOriginPatterns en vez de allowedOrigins
@Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOriginPatterns("http://localhost:4200")  // ← usar Patterns
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowCredentials(true);  // ← ahora sí funciona
}
```

---

## 🐳 PROBLEMAS DE DOCKER

### Problema 17: Docker Build Falla

**Error:**
```
ERROR [build 4/4] RUN mvn clean package -DskipTests
```

**Solución:**

```bash
# 1. Verificar que Dockerfile esté correcto
cat Dockerfile

# 2. Limpiar imágenes antiguas
docker system prune -a

# 3. Build sin caché
docker build --no-cache -t grossgym-backend:latest .

# 4. Ver logs completos
docker build -t grossgym-backend:latest . 2>&1 | tee build.log
```

---

### Problema 18: Container se Detiene Inmediatamente

**Verificar:**

```bash
# Ver logs del container
docker logs grossgym-backend

# Errores comunes en los logs:
# - "Communications link failure" → MySQL no accesible
# - "Unknown database" → BD no existe
# - "Access denied" → Credenciales incorrectas
```

**Solución:**

```bash
# Para conectar a MySQL del host desde Docker:
docker run -d \
  --name grossgym-backend \
  -p 8080:8080 \
  -e DB_HOST=host.docker.internal \
  --add-host host.docker.internal:host-gateway \
  grossgym-backend:latest
```

---

### Problema 19: No Puede Conectar a MySQL del Host

**Solución para macOS/Windows:**

```bash
# Usar host.docker.internal
docker run -d \
  --name grossgym-backend \
  -p 8080:8080 \
  -e DB_HOST=host.docker.internal \
  -e DB_PORT=3306 \
  -e DB_NAME=db_grossgym_fitness \
  -e DB_USER=jdanielmq \
  -e DB_PASS=JdmQ1481 \
  --add-host host.docker.internal:host-gateway \
  grossgym-backend:latest
```

**Solución para Linux:**

```bash
# Obtener IP del host
ip addr show docker0 | grep "inet " | awk '{print $2}' | cut -d/ -f1

# Usar esa IP
docker run -d \
  --name grossgym-backend \
  -p 8080:8080 \
  -e DB_HOST=172.17.0.1 \  # ← IP del host en Linux
  grossgym-backend:latest
```

---

## 📚 PROBLEMAS DE SWAGGER UI

### Problema 20: Swagger UI No Carga (404)

**Error:**
```
No static resource swagger-ui.
```

**Solución:**

```properties
# Verificar application.properties:
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui
springdoc.swagger-ui.enabled=true

# URL correcta:
# http://localhost:8080/api/swagger-ui/index.html
#                              ^^^^^ No olvidar el /index.html
```

---

### Problema 21: Swagger UI No Muestra Endpoints

**Causa:** Controllers sin anotaciones o paquete incorrecto.

**Solución:**

```java
// Verificar que los controllers tengan:
@RestController
@RequestMapping("/api/socios")
public class SocioController {
    // ...
}

// Verificar que GrossGymFitnessApplication.java escanee el paquete:
@SpringBootApplication
@EnableOpenApi  // Si es necesario
public class GrossGymFitnessApplication {
    // ...
}
```

---

## 🔗 PROBLEMAS DE INTEGRACIÓN

### Problema 22: Frontend No Puede Guardar Datos

**Error en Backend:**
```
400 Bad Request
Nothing to write: null body
```

**Causa:** Frontend envía objetos completos, backend espera solo IDs.

**Solución en Frontend:**

```typescript
// ❌ INCORRECTO:
const suscripcion = {
  socio: this.socioCompleto,  // Objeto completo
  plan: this.planCompleto,    // Objeto completo
  // ...
};

// ✅ CORRECTO:
const suscripcion: Suscripcion = {
  socio: { rut: this.socioSeleccionado },    // Solo RUT
  plan: { idPlan: Number(this.planSeleccionado) },  // Solo ID
  tipoPago: { idPago: Number(this.tipoPagoSeleccionado) },  // Solo ID
  // ...
};
```

---

### Problema 23: Campos de Formulario Bloqueados

**Problema:** Campos `readonly` no se pueden editar.

**Solución:**

```html
<!-- ❌ INCORRECTO: -->
<input type="number" [(ngModel)]="montoPlan" readonly>

<!-- ✅ CORRECTO: -->
<input type="number" [(ngModel)]="montoPlan" required min="0">
```

---

### Problema 24: Datos No Se Actualizan en el Select

**Problema:** Al cambiar el `<select>`, los datos no se actualizan.

**Solución:**

```typescript
// Convertir explícitamente a number
onPlanChange() {
  const planId = Number(this.planSeleccionado);
  
  const planEncontrado = this.planes.find(p => p.idPlan === planId);
  
  if (planEncontrado) {
    this.montoPlan = planEncontrado.montoPlan || 0;
    this.montoMatricula = planEncontrado.montoMatricula || 0;
  }
}
```

---

### Problema 25: Tabla Sin Headers Visibles

**Problema:** Headers de tabla no se ven después de cambiar estilos.

**Solución:**

```css
/* En styles.css, agregar !important si es necesario */
.table thead {
  background: linear-gradient(135deg, #2d2d2d, #1a1a1a) !important;
}

.table thead th {
  color: #ffffff !important;
  font-weight: 700 !important;
  padding: 16px !important;
}
```

---

## 🔍 HERRAMIENTAS DE DIAGNÓSTICO

### Verificar Estado General

```bash
# Backend
curl http://localhost:8080/api/planes
# Debe retornar JSON

# MySQL
mysql -u jdanielmq -p -e "SELECT 1"
# Debe retornar: 1

# Frontend
curl http://localhost:4200
# Debe retornar HTML

# Docker
docker ps
# Debe mostrar containers corriendo
```

### Ver Logs en Tiempo Real

```bash
# Backend (Maven)
mvn spring-boot:run

# Backend (JAR)
java -jar target/*.jar

# Frontend
ng serve

# Docker
docker logs -f grossgym-backend

# MySQL
sudo tail -f /var/log/mysql/error.log
```

---

## 📞 COMANDOS ÚTILES

### Reiniciar Todo

```bash
# 1. Detener todo
# Backend: Ctrl+C
# Frontend: Ctrl+C
# Docker: docker-compose down

# 2. Limpiar
cd /Users/juandanielmq/workspace-spring-boot-v3/crud-suscripcion-gym
mvn clean
cd frontend
rm -rf .angular node_modules
npm install

# 3. Reiniciar
# Terminal 1: Backend
mvn spring-boot:run

# Terminal 2: Frontend
cd frontend
ng serve
```

### Limpiar Cachés

```bash
# Maven
mvn clean
rm -rf ~/.m2/repository

# npm
cd frontend
rm -rf node_modules package-lock.json
npm cache clean --force
npm install

# Docker
docker system prune -a
docker volume prune

# Navegador
# Chrome: Ctrl+Shift+Del (Windows) / Cmd+Shift+Del (Mac)
# Firefox: Ctrl+Shift+Del
```

---

## 🆘 ÚLTIMA OPCIÓN: REINSTALACIÓN COMPLETA

Si nada funciona, reinstalar desde cero:

```bash
cd /Users/juandanielmq/workspace-spring-boot-v3/crud-suscripcion-gym

# 1. Eliminar todo
rm -rf target/
rm -rf frontend/node_modules frontend/.angular

# 2. Recrear BD
mysql -u jdanielmq -p < scripts/recreate-database.sql

# 3. Backend
mvn clean install -DskipTests
mvn spring-boot:run

# 4. Frontend (en otra terminal)
cd frontend
npm install
ng serve

# 5. Verificar
# Backend: http://localhost:8080/api/planes
# Frontend: http://localhost:4200
# Swagger: http://localhost:8080/api/swagger-ui/index.html
```

---

## 📚 RECURSOS ADICIONALES

- `docs/01-INICIO-RAPIDO.md` - Guía de inicio rápido
- `docs/02-INSTALACION.md` - Instalación completa
- `docs/03-DOCKER.md` - Uso de Docker
- `docs/04-BASE-DATOS.md` - Gestión de BD
- `docs/05-SWAGGER-UI.md` - Documentación API
- `docs/06-TEMA-GROSSGYM.md` - Tema visual

---

## ✅ CHECKLIST DE VERIFICACIÓN

Antes de reportar un problema, verifica:

- [ ] Java 17 instalado: `java --version`
- [ ] Maven funciona: `mvn --version`
- [ ] Node.js instalado: `node --version`
- [ ] Angular CLI instalado: `ng version`
- [ ] MySQL corriendo: `mysql -u jdanielmq -p`
- [ ] BD existe: `mysql -u jdanielmq -p db_grossgym_fitness`
- [ ] Puerto 8080 libre: `lsof -i :8080`
- [ ] Puerto 4200 libre: `lsof -i :4200`
- [ ] Backend compila: `mvn clean package`
- [ ] Frontend compila: `ng build`
- [ ] Logs sin errores

---

**¡Si sigues teniendo problemas, revisa los logs completos y busca el mensaje de error específico! 🔍**

