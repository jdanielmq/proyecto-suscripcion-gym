# 🐳 Guía Completa de Docker - Gross Gym Fitness

> **Sistema de Gestión de Suscripciones**  
> Backend: Spring Boot 3 + Java 17 + MySQL

---

## 📋 TABLA DE CONTENIDOS

1. [Pre-requisitos](#pre-requisitos)
2. [Arquitectura Docker](#arquitectura-docker)
3. [Opción 1: Solo Backend en Docker](#opción-1-solo-backend-en-docker-recomendado)
4. [Opción 2: Backend + MySQL en Docker](#opción-2-backend--mysql-en-docker)
5. [Dockerfile Explicado](#dockerfile-explicado)
6. [Docker Compose Explicado](#docker-compose-explicado)
7. [Comandos Docker Útiles](#comandos-docker-útiles)
8. [Troubleshooting](#troubleshooting)

---

## 📌 PRE-REQUISITOS

### ✅ Software Necesario

```bash
# 1. Docker Desktop instalado y corriendo
docker --version
# Salida esperada: Docker version 24.x.x o superior

# 2. Docker Compose instalado
docker-compose --version
# Salida esperada: docker-compose version 2.x.x o superior

# 3. MySQL corriendo en el host (para Opción 1)
mysql --version
# Salida esperada: mysql  Ver 8.x.x
```

### 📦 Verificar Docker

```bash
# Verificar que Docker Desktop esté corriendo
docker ps

# Verificar imágenes disponibles
docker images

# Verificar redes
docker network ls
```

---

## 🏗️ ARQUITECTURA DOCKER

### Opción 1: Backend en Docker + MySQL en Host (RECOMENDADO)

```
┌─────────────────────────────────────────────────┐
│  HOST (macOS/Windows/Linux)                     │
│                                                  │
│  ┌──────────────────┐      ┌─────────────────┐ │
│  │  MySQL (Host)    │◄─────┤  Docker:        │ │
│  │  Port: 3306      │      │  Backend:8080   │ │
│  │  User: jdanielmq │      │  (Container)    │ │
│  └──────────────────┘      └─────────────────┘ │
│                                    ▲             │
│                                    │             │
│  ┌─────────────────────────────────┘             │
│  │  Angular Frontend (Host)                     │
│  │  Port: 4200                                  │
│  └──────────────────────────────────────────────┘
│                                                  │
└─────────────────────────────────────────────────┘
```

**Ventajas:**
- ✅ MySQL ya configurado en el host
- ✅ No afecta otros sistemas
- ✅ Más rápido para desarrollo
- ✅ Fácil acceso a la BD con herramientas locales

### Opción 2: Backend + MySQL en Docker

```
┌─────────────────────────────────────────────────┐
│  Docker Network: grossgym-network               │
│                                                  │
│  ┌──────────────────┐      ┌─────────────────┐ │
│  │  MySQL           │◄─────┤  Backend        │ │
│  │  (Container)     │      │  (Container)    │ │
│  │  Port: 3306      │      │  Port: 8080     │ │
│  └──────────────────┘      └─────────────────┘ │
│         ▲                           ▲           │
│         │                           │           │
└─────────┼───────────────────────────┼───────────┘
          │                           │
    Port 3306                    Port 8080
          │                           │
┌─────────▼───────────────────────────▼───────────┐
│  HOST: Angular Frontend (Port 4200)             │
└─────────────────────────────────────────────────┘
```

**Ventajas:**
- ✅ Ambiente aislado y reproducible
- ✅ Ideal para CI/CD y producción
- ✅ No depende de MySQL local

---

## 🚀 OPCIÓN 1: SOLO BACKEND EN DOCKER (RECOMENDADO)

### Paso 1: Verificar MySQL Local

```bash
# Verificar que MySQL esté corriendo
mysql -u jdanielmq -p

# Entrar a MySQL y verificar la base de datos
mysql> SHOW DATABASES;
mysql> USE db_grossgym_fitness;
mysql> SHOW TABLES;
mysql> EXIT;
```

### Paso 2: Construir la Imagen Docker

```bash
# Ir al directorio del proyecto
cd /Users/juandanielmq/workspace-spring-boot-v3/crud-suscripcion-gym

# Construir la imagen del backend
docker build -t grossgym-backend:latest .
```

**Salida esperada:**
```
[+] Building 45.2s (12/12) FINISHED
 => [internal] load build definition from Dockerfile
 => => transferring dockerfile: 1.23kB
 => [internal] load .dockerignore
 => [build 1/4] FROM maven:3.9.6-eclipse-temurin-17
 => [build 2/4] WORKDIR /app
 => [build 3/4] COPY pom.xml .
 => [build 4/4] COPY src ./src
 => [build 5/4] RUN mvn clean package -DskipTests
 => [stage-1 1/5] FROM eclipse-temurin:17-jre-alpine
 => [stage-1 2/5] WORKDIR /app
 => [stage-1 3/5] RUN apk add --no-cache wget
 => [stage-1 4/5] RUN addgroup -S spring && adduser -S spring -G spring
 => [stage-1 5/5] COPY --from=build /app/target/*.jar app.jar
 => exporting to image
 => => exporting layers
 => => writing image sha256:abc123...
 => => naming to docker.io/library/grossgym-backend:latest
```

### Paso 3: Ejecutar el Container

```bash
# Ejecutar el backend conectándose a MySQL local
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

**Explicación de parámetros:**
- `-d`: Ejecutar en background (detached)
- `--name`: Nombre del container
- `-p 8080:8080`: Mapear puerto 8080 del container al host
- `-e`: Variables de entorno para configuración
- `--add-host`: Permitir acceso a MySQL del host

### Paso 4: Verificar que Esté Corriendo

```bash
# Ver logs del container
docker logs -f grossgym-backend

# Verificar que esté corriendo
docker ps

# Verificar el health check
docker inspect grossgym-backend | grep -A 10 Health
```

**Salida esperada en logs:**
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-11-27 INFO  Starting GrossGymFitnessApplication
2024-11-27 INFO  Started GrossGymFitnessApplication in 5.234 seconds
```

### Paso 5: Probar el Backend

```bash
# Probar endpoint de planes
curl http://localhost:8080/api/planes

# Probar Swagger UI
open http://localhost:8080/api/swagger-ui/index.html
```

### Paso 6: Ejecutar Frontend (en el host)

```bash
# En otra terminal, ejecutar Angular
cd frontend
ng serve

# Abrir en navegador
open http://localhost:4200
```

---

## 🐋 OPCIÓN 2: BACKEND + MYSQL EN DOCKER

### Configuración Completa con Docker Compose

Esta opción crea un ambiente completamente aislado.

### Paso 1: Preparar docker-compose.yml

Ya está listo en el proyecto, pero aquí está la estructura:

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    container_name: grossgym-mysql
    ports:
      - "3307:3306"  # Puerto 3307 en host para no conflicto
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: db_grossgym_fitness
      MYSQL_USER: grossgym_user
      MYSQL_PASSWORD: grossgym_pass
    volumes:
      - mysql_data:/var/lib/mysql
      - ./scripts/init.sql:/docker-entrypoint-initdb.d/init.sql

  backend:
    build: .
    container_name: grossgym-backend
    ports:
      - "8080:8080"
    environment:
      DB_HOST: mysql
      DB_PORT: 3306
      DB_NAME: db_grossgym_fitness
      DB_USER: grossgym_user
      DB_PASS: grossgym_pass
    depends_on:
      - mysql

volumes:
  mysql_data:
```

### Paso 2: Iniciar Todo con Docker Compose

```bash
# Iniciar todos los servicios
docker-compose up -d

# Ver logs de ambos servicios
docker-compose logs -f

# Ver logs solo del backend
docker-compose logs -f backend

# Ver logs solo de MySQL
docker-compose logs -f mysql
```

### Paso 3: Verificar

```bash
# Verificar que ambos estén corriendo
docker-compose ps

# Conectarse a MySQL del container
docker exec -it grossgym-mysql mysql -u grossgym_user -pgrossgym_pass db_grossgym_fitness

# Verificar tablas
mysql> SHOW TABLES;
mysql> SELECT * FROM plan;
```

### Paso 4: Detener Todo

```bash
# Detener servicios pero mantener datos
docker-compose stop

# Detener y eliminar containers (mantiene volúmenes)
docker-compose down

# Detener, eliminar containers Y volúmenes (borra la BD)
docker-compose down -v
```

---

## 📖 DOCKERFILE EXPLICADO

```dockerfile
# ========================================
# Stage 1: Build (Compilación)
# ========================================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar archivos
COPY pom.xml .
COPY src ./src

# Compilar (skip tests para ser más rápido)
RUN mvn clean package -DskipTests

# ========================================
# Stage 2: Runtime (Ejecución)
# ========================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Instalar wget para health check
RUN apk add --no-cache wget

# Crear usuario no-root (seguridad)
RUN addgroup -S spring && adduser -S spring -G spring

# Copiar JAR compilado desde stage build
COPY --from=build /app/target/*.jar app.jar

# Permisos
RUN chown spring:spring app.jar

# Ejecutar como usuario no-root
USER spring:spring

# Puerto
EXPOSE 8080

# Variables de entorno
ENV SPRING_PROFILES_ACTIVE=dev \
    JAVA_OPTS="-Xms256m -Xmx512m" \
    DB_HOST=host.docker.internal \
    DB_PORT=3306 \
    DB_NAME=db_grossgym_fitness \
    DB_USER=jdanielmq \
    DB_PASS=JdmQ1481

# Health check
HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/api/planes || exit 1

# Comando de inicio
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.datasource.url=jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME} -Dspring.datasource.username=${DB_USER} -Dspring.datasource.password=${DB_PASS} -jar app.jar"]
```

### ¿Por qué Multi-Stage Build?

**Stage 1 (Build):** 
- Usa imagen Maven completa (pesada, ~700MB)
- Compila el proyecto
- Genera el JAR

**Stage 2 (Runtime):**
- Usa imagen JRE Alpine (liviana, ~170MB)
- Solo copia el JAR compilado
- **Resultado:** Imagen final mucho más pequeña

**Comparación de tamaños:**
```
Single-stage:  ~750 MB
Multi-stage:   ~220 MB  ✅ (3x más pequeña!)
```

---

## 🔧 DOCKER COMPOSE EXPLICADO

```yaml
version: '3.8'

services:
  backend:
    build:
      context: .           # Carpeta donde está el Dockerfile
      dockerfile: Dockerfile
    container_name: grossgym-backend
    ports:
      - "8080:8080"       # Host:Container
    environment:
      # Variables de conexión a MySQL
      DB_HOST: host.docker.internal  # Especial para conectar al host
      DB_PORT: 3306
      DB_NAME: db_grossgym_fitness
      DB_USER: jdanielmq
      DB_PASS: JdmQ1481
      JAVA_OPTS: "-Xms256m -Xmx512m"  # Config JVM
    extra_hosts:
      - "host.docker.internal:host-gateway"  # Permite acceso al host
    networks:
      - grossgym-network
    restart: unless-stopped  # Reiniciar automáticamente
    healthcheck:
      test: ["CMD", "wget", "--spider", "http://localhost:8080/api/planes"]
      interval: 30s
      timeout: 5s
      retries: 3

networks:
  grossgym-network:
    driver: bridge  # Red interna entre containers
```

---

## 🛠️ COMANDOS DOCKER ÚTILES

### Gestión de Imágenes

```bash
# Listar todas las imágenes
docker images

# Construir imagen
docker build -t grossgym-backend:latest .

# Construir sin caché (forzar rebuild completo)
docker build --no-cache -t grossgym-backend:latest .

# Eliminar imagen
docker rmi grossgym-backend:latest

# Eliminar imágenes sin usar
docker image prune

# Eliminar todas las imágenes sin usar (incluyendo colgadas)
docker image prune -a
```

### Gestión de Containers

```bash
# Listar containers corriendo
docker ps

# Listar todos los containers (incluyendo detenidos)
docker ps -a

# Iniciar container
docker start grossgym-backend

# Detener container
docker stop grossgym-backend

# Reiniciar container
docker restart grossgym-backend

# Eliminar container
docker rm grossgym-backend

# Eliminar container forzado (si está corriendo)
docker rm -f grossgym-backend

# Eliminar todos los containers detenidos
docker container prune
```

### Logs y Debugging

```bash
# Ver logs
docker logs grossgym-backend

# Ver logs en tiempo real
docker logs -f grossgym-backend

# Ver últimas 100 líneas
docker logs --tail 100 grossgym-backend

# Entrar al container (interactivo)
docker exec -it grossgym-backend sh

# Ver procesos corriendo en el container
docker top grossgym-backend

# Ver estadísticas de uso (CPU, RAM)
docker stats grossgym-backend

# Inspeccionar configuración completa
docker inspect grossgym-backend
```

### Docker Compose

```bash
# Iniciar servicios (crea y arranca)
docker-compose up -d

# Iniciar y reconstruir imágenes
docker-compose up -d --build

# Ver logs
docker-compose logs -f

# Ver logs de un servicio específico
docker-compose logs -f backend

# Detener servicios
docker-compose stop

# Iniciar servicios detenidos
docker-compose start

# Reiniciar servicios
docker-compose restart

# Ver estado
docker-compose ps

# Detener y eliminar containers
docker-compose down

# Detener, eliminar containers y volúmenes
docker-compose down -v

# Ejecutar comando en un servicio
docker-compose exec backend sh
```

### Limpieza General

```bash
# Eliminar todo lo que no se está usando
docker system prune

# Eliminar TODO (containers, imágenes, volúmenes, redes)
docker system prune -a --volumes

# Ver espacio usado por Docker
docker system df
```

---

## 🔍 TROUBLESHOOTING

### Problema 1: No Puede Conectar a MySQL del Host

**Error:**
```
Communications link failure
```

**Solución:**

```bash
# 1. Verificar que MySQL esté corriendo
mysql -u jdanielmq -p

# 2. Asegurarse de usar host.docker.internal
docker run -d \
  --name grossgym-backend \
  -p 8080:8080 \
  -e DB_HOST=host.docker.internal \
  --add-host host.docker.internal:host-gateway \
  grossgym-backend:latest

# 3. En macOS, verificar que MySQL acepte conexiones externas
# Editar /etc/my.cnf y verificar:
bind-address = 0.0.0.0
```

### Problema 2: Puerto 8080 Ya en Uso

**Error:**
```
Bind for 0.0.0.0:8080 failed: port is already allocated
```

**Solución:**

```bash
# Ver qué está usando el puerto 8080
lsof -i :8080

# Opción 1: Matar el proceso
kill -9 [PID]

# Opción 2: Usar otro puerto
docker run -d \
  --name grossgym-backend \
  -p 8081:8080 \  # Mapear 8081 del host a 8080 del container
  grossgym-backend:latest

# Ahora acceder en: http://localhost:8081
```

### Problema 3: El Container se Detiene Inmediatamente

**Solución:**

```bash
# Ver los logs para identificar el error
docker logs grossgym-backend

# Errores comunes:
# - Base de datos no existe → Crear la BD primero
# - Usuario/password incorrectos → Verificar credenciales
# - Puerto ya en uso → Cambiar el puerto
```

### Problema 4: Imagen Muy Grande

**Solución:**

```bash
# Ya usamos multi-stage build, pero puedes:

# 1. Ver capas de la imagen
docker history grossgym-backend:latest

# 2. Limpiar caché de Maven dentro del Dockerfile
# Agregar al final del stage build:
RUN mvn clean

# 3. Usar .dockerignore para excluir archivos innecesarios
echo "target/" >> .dockerignore
echo "node_modules/" >> .dockerignore
echo ".git/" >> .dockerignore
```

### Problema 5: Build Muy Lento

**Solución:**

```bash
# 1. Usar caché de capas de Docker correctamente
# El orden importa: archivos que cambian menos primero

# 2. Descargar dependencias en capa separada
COPY pom.xml .
RUN mvn dependency:go-offline  # Solo si cambia pom.xml

COPY src ./src
RUN mvn clean package -DskipTests  # Solo si cambia código

# 3. Usar BuildKit (más rápido)
DOCKER_BUILDKIT=1 docker build -t grossgym-backend:latest .
```

### Problema 6: Health Check Falla

**Error:**
```
unhealthy
```

**Solución:**

```bash
# Verificar manualmente
docker exec -it grossgym-backend wget --spider http://localhost:8080/api/planes

# Si falla, verificar:
# 1. Que el endpoint exista
curl http://localhost:8080/api/planes

# 2. Ajustar el tiempo de inicio (start-period)
# En el Dockerfile, aumentar de 60s a 120s

# 3. Ver logs
docker logs grossgym-backend
```

---

## ✅ CHECKLIST DE VERIFICACIÓN

### Antes de Dockerizar

- [ ] Proyecto compila localmente: `mvn clean package`
- [ ] MySQL corriendo y accesible
- [ ] Base de datos `db_grossgym_fitness` existe
- [ ] Usuario `jdanielmq` tiene permisos
- [ ] Puerto 8080 libre

### Después de Dockerizar

- [ ] Imagen construida: `docker images | grep grossgym`
- [ ] Container corriendo: `docker ps | grep grossgym`
- [ ] Logs sin errores: `docker logs grossgym-backend`
- [ ] Health check OK: `docker inspect grossgym-backend | grep Health`
- [ ] API responde: `curl http://localhost:8080/api/planes`
- [ ] Swagger accesible: `http://localhost:8080/api/swagger-ui/index.html`

---

## 📚 REFERENCIAS

- [Docker Documentation](https://docs.docker.com/)
- [Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)
- [Multi-stage builds](https://docs.docker.com/build/building/multi-stage/)
- [Docker Compose Documentation](https://docs.docker.com/compose/)

---

## 🚀 SIGUIENTE PASO

**Después de tener Docker funcionando:**

1. ✅ **Backend en Docker:** Verificado
2. ⏭️ **Frontend:** Ejecutar `ng serve` en el host
3. ⏭️ **Probar integración:** http://localhost:4200
4. ⏭️ **Swagger UI:** http://localhost:8080/api/swagger-ui/index.html

---

**¡Docker configurado! 🎉**

Para más información, consulta los otros documentos:
- `01-INICIO.md` - Guía de inicio rápido
- `02-EJECUTAR-PROYECTO.md` - Cómo ejecutar sin Docker
- `04-BASE-DATOS.md` - Configuración de base de datos
- `05-SWAGGER-UI.md` - Documentación de API

