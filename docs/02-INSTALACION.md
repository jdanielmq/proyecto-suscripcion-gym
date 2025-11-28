# 📦 Guía de Instalación Completa - Gross Gym Fitness

> Sistema de Gestión de Suscripciones  
> Spring Boot 3 + Java 17 + Angular 18 + MySQL

---

## 📋 TABLA DE CONTENIDOS

1. [Requisitos del Sistema](#requisitos-del-sistema)
2. [Instalación de Herramientas](#instalación-de-herramientas)
3. [Configuración del Proyecto](#configuración-del-proyecto)
4. [Configuración de Base de Datos](#configuración-de-base-de-datos)
5. [Ejecución del Proyecto](#ejecución-del-proyecto)
6. [Verificación](#verificación)

---

## 💻 REQUISITOS DEL SISTEMA

### Mínimos

| Componente | Versión Mínima | Recomendada |
|------------|----------------|-------------|
| Java | 17 | 17 o 21 |
| Maven | 3.8.1 | 3.9.6 |
| Node.js | 18.x | 20.x |
| Angular CLI | 18.x | 18.x |
| MySQL | 8.0 | 8.0.35+ |
| RAM | 4 GB | 8 GB |
| Disco | 2 GB | 5 GB |

### Sistema Operativo

- ✅ macOS 11+ (Big Sur o superior)
- ✅ Windows 10/11
- ✅ Linux (Ubuntu 20.04+, Fedora, etc.)

---

## 🛠️ INSTALACIÓN DE HERRAMIENTAS

### 1. Java 17 (JDK)

#### macOS (Homebrew)

```bash
# Instalar OpenJDK 17
brew install openjdk@17

# Configurar JAVA_HOME
echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 17)' >> ~/.zshrc
source ~/.zshrc

# Verificar
java --version
# java version "17.0.x" ...
```

#### Windows (Chocolatey)

```powershell
# Instalar OpenJDK 17
choco install openjdk17

# Verificar
java --version
```

#### Linux (apt)

```bash
# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-17-jdk

# Verificar
java --version
```

### 2. Maven

#### macOS

```bash
brew install maven

# Verificar
mvn --version
# Apache Maven 3.9.x
```

#### Windows

```powershell
choco install maven

# Verificar
mvn --version
```

#### Linux

```bash
sudo apt install maven

# Verificar
mvn --version
```

### 3. Node.js y npm

#### macOS

```bash
# Opción 1: Homebrew
brew install node@20

# Opción 2: nvm (recomendado)
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.0/install.sh | bash
nvm install 20
nvm use 20

# Verificar
node --version  # v20.x.x
npm --version   # 10.x.x
```

#### Windows

```powershell
# Descargar e instalar desde:
# https://nodejs.org/

# O con Chocolatey:
choco install nodejs-lts

# Verificar
node --version
npm --version
```

#### Linux

```bash
# Ubuntu/Debian
curl -fsSL https://deb.nodesource.com/setup_20.x | sudo -E bash -
sudo apt-get install -y nodejs

# Verificar
node --version
npm --version
```

### 4. Angular CLI

```bash
# Instalar globalmente
npm install -g @angular/cli@18

# Verificar
ng version
# Angular CLI: 18.x.x
```

### 5. MySQL 8.0

#### macOS

```bash
# Opción 1: Homebrew
brew install mysql@8.0
brew services start mysql@8.0

# Opción 2: MySQL Installer
# Descargar desde: https://dev.mysql.com/downloads/mysql/

# Configurar usuario root
mysql_secure_installation

# Verificar
mysql --version
# mysql  Ver 8.0.x
```

#### Windows

```powershell
# Opción 1: Chocolatey
choco install mysql

# Opción 2: MySQL Installer
# Descargar desde: https://dev.mysql.com/downloads/installer/

# Iniciar servicio
net start MySQL80

# Verificar
mysql --version
```

#### Linux

```bash
# Ubuntu/Debian
sudo apt update
sudo apt install mysql-server

# Iniciar servicio
sudo systemctl start mysql
sudo systemctl enable mysql

# Configurar seguridad
sudo mysql_secure_installation

# Verificar
mysql --version
```

---

## ⚙️ CONFIGURACIÓN DEL PROYECTO

### 1. Clonar o Navegar al Proyecto

```bash
cd /Users/juandanielmq/workspace-spring-boot-v3/crud-suscripcion-gym

# Ver estructura
ls -la
```

**Estructura esperada:**
```
crud-suscripcion-gym/
├── src/                    # Código fuente Java
├── frontend/               # Código fuente Angular
├── scripts/                # Scripts SQL
├── docs/                   # Documentación
├── pom.xml                 # Configuración Maven
├── Dockerfile              # Configuración Docker
└── docker-compose.yml      # Orquestación Docker
```

### 2. Configurar Backend

#### Verificar application.properties

```bash
cat src/main/resources/application.properties
```

**Debe contener:**
```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/db_grossgym_fitness
spring.datasource.username=jdanielmq
spring.datasource.password=JdmQ1481
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl

# Server Configuration
server.port=8080
server.servlet.context-path=/api

# Swagger Configuration
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui
springdoc.swagger-ui.operationsSorter=method
springdoc.swagger-ui.tagsSorter=alpha
```

### 3. Configurar Frontend

```bash
cd frontend

# Verificar package.json
cat package.json
```

**Verificar que tenga:**
```json
{
  "name": "grossgym-fitness-frontend",
  "version": "1.0.0",
  "scripts": {
    "ng": "ng",
    "start": "ng serve",
    "build": "ng build",
    "watch": "ng build --watch --configuration development",
    "test": "ng test"
  },
  "dependencies": {
    "@angular/animations": "^18.0.0",
    "@angular/common": "^18.0.0",
    "@angular/compiler": "^18.0.0",
    "@angular/core": "^18.0.0",
    "@angular/forms": "^18.0.0",
    "@angular/platform-browser": "^18.0.0",
    "@angular/router": "^18.0.0",
    "bootstrap": "^5.3.0",
    "rxjs": "~7.8.0"
  }
}
```

#### Configurar URL del Backend

```bash
# Verificar que los servicios apunten a localhost:8080
grep -r "localhost:8080" frontend/src/app/services/
```

**Debe mostrar:**
```typescript
private apiUrl = 'http://localhost:8080/api/socios';
private apiUrl = 'http://localhost:8080/api/suscripciones';
private apiUrl = 'http://localhost:8080/api/planes';
// etc...
```

---

## 🗄️ CONFIGURACIÓN DE BASE DE DATOS

### 1. Crear Usuario MySQL

```bash
# Conectarse como root
mysql -u root -p

# Crear usuario (si no existe)
CREATE USER 'jdanielmq'@'localhost' IDENTIFIED BY 'JdmQ1481';
GRANT ALL PRIVILEGES ON *.* TO 'jdanielmq'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

### 2. Crear Base de Datos y Tablas

```bash
# Opción 1: Script completo
mysql -u jdanielmq -p < scripts/recreate-database.sql

# Opción 2: Paso a paso
mysql -u jdanielmq -p

# Crear BD
CREATE DATABASE IF NOT EXISTS db_grossgym_fitness;
USE db_grossgym_fitness;

# Ejecutar script de tablas
SOURCE scripts/create-tables.sql;

# Verificar
SHOW TABLES;
```

**Tablas esperadas:**
```
+--------------------------------+
| Tables_in_db_grossgym_fitness |
+--------------------------------+
| tipo_pago                      |
| plan                           |
| estado                         |
| socio                          |
| suscripcion                    |
| instructor                     |
| inbody                         |
+--------------------------------+
```

### 3. Insertar Datos de Prueba (Opcional)

```bash
mysql -u jdanielmq -p db_grossgym_fitness < scripts/init.sql
```

**Verificar datos:**
```sql
SELECT * FROM tipo_pago;
SELECT * FROM plan;
SELECT * FROM estado;
```

---

## ▶️ EJECUCIÓN DEL PROYECTO

### 1. Iniciar Backend

```bash
# Volver a la raíz del proyecto
cd /Users/juandanielmq/workspace-spring-boot-v3/crud-suscripcion-gym

# Opción A: Con Maven (desarrollo)
mvn spring-boot:run

# Opción B: Compilar y ejecutar JAR
mvn clean package -DskipTests
java -jar target/grossgym-fitness-0.0.1-SNAPSHOT.jar

# Opción C: Con IDE (IntelliJ, Eclipse, VS Code)
# Abrir proyecto → Run GrossGymFitnessApplication.java
```

**Salida esperada:**
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-11-27 INFO  Starting GrossGymFitnessApplication
2024-11-27 INFO  Tomcat started on port(s): 8080 (http)
2024-11-27 INFO  Started GrossGymFitnessApplication in 5.234 seconds
```

### 2. Iniciar Frontend

```bash
# En otra terminal
cd frontend

# Instalar dependencias (solo la primera vez)
npm install

# Iniciar servidor de desarrollo
ng serve

# O en modo abierto automático
ng serve --open
```

**Salida esperada:**
```
✔ Browser application bundle generation complete.
✔ Compiled successfully.

Initial Chunk Files | Names         |  Raw Size
polyfills.js        | polyfills     | 314.27 kB |
main.js             | main          |  50.38 kB |
styles.css          | styles        |  45.25 kB |

                    | Initial Total | 409.90 kB

Application bundle generation complete. [5.123 seconds]

** Angular Live Development Server is listening on localhost:4200 **
✔ Compiled successfully.
```

---

## ✅ VERIFICACIÓN

### 1. Verificar Backend

```bash
# Test 1: Health check básico
curl http://localhost:8080/api/planes

# Respuesta esperada: JSON con lista de planes
# [{"idPlan":1,"tipoPlan":"Mensual",...}]

# Test 2: Swagger UI
# Abrir en navegador:
open http://localhost:8080/api/swagger-ui/index.html
```

### 2. Verificar Frontend

```bash
# Abrir en navegador:
open http://localhost:4200

# Verificar rutas:
# http://localhost:4200/socios
# http://localhost:4200/planes
# http://localhost:4200/suscripciones
```

### 3. Verificar Integración

```bash
# En el frontend, intentar:
# 1. Listar planes → Debe mostrar datos
# 2. Crear un socio → Debe guardar correctamente
# 3. Crear una suscripción → Debe funcionar

# Si hay error CORS:
# - Verificar que CorsConfig.java tenga http://localhost:4200
# - Reiniciar backend
```

---

## 🔧 CONFIGURACIÓN ADICIONAL

### Variables de Entorno (Opcional)

```bash
# Crear archivo .env en la raíz (no commitear)
cat > .env << EOF
DB_HOST=localhost
DB_PORT=3306
DB_NAME=db_grossgym_fitness
DB_USER=jdanielmq
DB_PASS=JdmQ1481
SERVER_PORT=8080
EOF

# Cargar variables
export $(cat .env | xargs)
```

### Cambiar Puerto del Backend

```bash
# Editar application.properties
server.port=8081

# Actualizar en servicios del frontend
# frontend/src/app/services/*.service.ts
private apiUrl = 'http://localhost:8081/api/...';
```

### Cambiar Puerto del Frontend

```bash
# Iniciar en puerto diferente
ng serve --port 4201

# Actualizar CORS en backend
# src/main/java/com/grossgym/fitness/config/CorsConfig.java
.allowedOriginPatterns("http://localhost:4201")
```

---

## 📁 ESTRUCTURA DEL PROYECTO

```
crud-suscripcion-gym/
│
├── src/main/java/com/grossgym/fitness/
│   ├── config/                    # Configuraciones (CORS, Swagger)
│   ├── controller/                # REST Controllers
│   ├── service/                   # Lógica de negocio
│   │   └── impl/                  # Implementaciones
│   ├── repository/                # JPA Repositories
│   ├── model/                     # Entidades JPA
│   └── GrossGymFitnessApplication.java
│
├── src/main/resources/
│   └── application.properties     # Configuración de Spring
│
├── frontend/src/app/
│   ├── components/                # Componentes Angular
│   │   ├── socios/
│   │   ├── planes/
│   │   └── suscripciones/
│   ├── services/                  # Servicios HTTP
│   ├── models/                    # Interfaces TypeScript
│   ├── app.routes.ts              # Rutas
│   └── app.component.ts           # Componente raíz
│
├── scripts/                       # Scripts SQL
│   ├── create-tables.sql
│   ├── init.sql
│   └── recreate-database.sql
│
├── docs/                          # Documentación
│   ├── 01-INICIO-RAPIDO.md
│   ├── 02-INSTALACION.md
│   ├── 03-DOCKER.md
│   └── ...
│
├── pom.xml                        # Dependencias Maven
├── Dockerfile                     # Imagen Docker
├── docker-compose.yml             # Orquestación
└── README.md                      # Documentación principal
```

---

## 🚀 SIGUIENTE PASO

Ahora que tienes todo instalado y configurado:

1. **Explorar el código:** Revisa la estructura del proyecto
2. **Probar funcionalidades:** Crear socios, planes, suscripciones
3. **Ver Swagger UI:** Documentación interactiva de la API
4. **Dockerizar:** Ejecutar con Docker (ver `docs/03-DOCKER.md`)
5. **Personalizar:** Adaptar el proyecto a tus necesidades

---

## 📖 DOCUMENTACIÓN ADICIONAL

- `docs/01-INICIO-RAPIDO.md` - Guía rápida (5 minutos)
- `docs/03-DOCKER.md` - Ejecutar con Docker
- `docs/04-BASE-DATOS.md` - Esquema y configuración de BD
- `docs/05-SWAGGER-UI.md` - Documentación de API
- `docs/06-TEMA-GROSSGYM.md` - Personalización visual
- `docs/07-SOLUCIONES-COMUNES.md` - Troubleshooting

---

**¡Instalación completa! 🎉**

Si tienes problemas, consulta `docs/07-SOLUCIONES-COMUNES.md`

