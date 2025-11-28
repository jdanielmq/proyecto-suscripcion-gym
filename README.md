# 🏋️ Gross Gym Fitness - Sistema de Gestión de Suscripciones

![Java](https://img.shields.io/badge/Java-17-orange?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-green?logo=spring)
![Angular](https://img.shields.io/badge/Angular-20-red?logo=angular)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)
![Docker](https://img.shields.io/badge/Docker-Ready-blue?logo=docker)
![License](https://img.shields.io/badge/License-MIT-yellow)

> Sistema monolítico completo para la gestión de suscripciones, socios y planes de un gimnasio.  
> Inspirado en [Gross Gym](https://grossgym.cl/)

---

## 📚 DOCUMENTACIÓN COMPLETA

**Lee los documentos en orden según tus necesidades:**

| # | Documento | Cuándo Leer | Tiempo |
|---|-----------|-------------|---------|
| 🚀 | [`docs/01-INICIO-RAPIDO.md`](docs/01-INICIO-RAPIDO.md) | **Primera vez aquí** | 5 min |
| 📦 | [`docs/02-INSTALACION.md`](docs/02-INSTALACION.md) | Instalación completa | 15 min |
| 🐳 | [`docs/03-DOCKER.md`](docs/03-DOCKER.md) | Ejecutar con Docker | 10 min |
| 🗄️ | [`docs/04-BASE-DATOS.md`](docs/04-BASE-DATOS.md) | Gestión de MySQL | 5 min |
| 📖 | [`docs/05-SWAGGER-UI.md`](docs/05-SWAGGER-UI.md) | Uso de Swagger UI | 5 min |
| 🎨 | [`docs/06-TEMA-GROSSGYM.md`](docs/06-TEMA-GROSSGYM.md) | Diseño y estilos | 5 min |
| 🔧 | [`docs/07-SOLUCIONES-COMUNES.md`](docs/07-SOLUCIONES-COMUNES.md) | **Problemas?** Troubleshooting | Variable |
| 🧪 | [`docs/08-TESTING-LOGGING.md`](docs/08-TESTING-LOGGING.md) | **Tests y Logging** | 15 min |
| 📘 | [`DOCUMENTACION.md`](DOCUMENTACION.md) | Documentación técnica completa | 20 min |

### 🎯 Guías Rápidas por Situación

**¿Primera vez con el proyecto?**
→ Lee [`docs/01-INICIO-RAPIDO.md`](docs/01-INICIO-RAPIDO.md)

**¿Necesitas instalar desde cero?**
→ Lee [`docs/02-INSTALACION.md`](docs/02-INSTALACION.md)

**¿Quieres usar Docker?**
→ Lee [`docs/03-DOCKER.md`](docs/03-DOCKER.md)

**¿Tienes un error?**
→ Lee [`docs/07-SOLUCIONES-COMUNES.md`](docs/07-SOLUCIONES-COMUNES.md)

**¿Quieres personalizar el diseño?**
→ Lee [`docs/06-TEMA-GROSSGYM.md`](docs/06-TEMA-GROSSGYM.md)

---

## ⚡ INICIO ULTRA-RÁPIDO

```bash
# 1. Clonar o navegar al proyecto
cd /Users/juandanielmq/workspace-spring-boot-v3/crud-suscripcion-gym

# 2. Configurar base de datos
mysql -u jdanielmq -p < scripts/recreate-database.sql

# 3. Iniciar backend (Terminal 1)
mvn spring-boot:run

# 4. Iniciar frontend (Terminal 2)
cd frontend && ng serve

# 5. Abrir en navegador
# Frontend: http://localhost:4200
# API: http://localhost:8080/api
# Swagger UI: http://localhost:8080/api/swagger-ui/index.html
```

**¿No funciona?** → [`docs/07-SOLUCIONES-COMUNES.md`](docs/07-SOLUCIONES-COMUNES.md)

---

## 📋 TABLA DE CONTENIDOS

- [Características](#-características)
- [Tecnologías](#-tecnologías)
- [Arquitectura](#-arquitectura)
- [Requisitos Previos](#-requisitos-previos)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [API Endpoints](#-api-endpoints)
- [Docker](#-docker)
- [Screenshots](#-screenshots)
- [Contribución](#-contribución)
- [Licencia](#-licencia)

---

## ✨ CARACTERÍSTICAS

### Backend (Spring Boot 3 + Java 17)
- ✅ **CRUD completo** de Socios, Suscripciones, Planes
- ✅ **API RESTful** con Spring Boot 3.2.0
- ✅ **Swagger UI** / OpenAPI 3 para documentación interactiva
- ✅ **JPA/Hibernate** con MySQL 8.0
- ✅ **Validaciones** de negocio y datos
- ✅ **Cálculo automático** de fechas de término de suscripciones
- ✅ **CORS** configurado para frontend Angular
- ✅ **Arquitectura en capas** (Controller → Service → Repository → Entity)
- ✅ **Gestión de Estados** de suscripción (Activo, Vencido, Suspendido, etc.)
- ✅ **Evaluaciones InBody** con almacenamiento JSON

### Frontend (Angular 20 + Bootstrap 5)
- ✅ **Interfaz moderna** inspirada en [Gross Gym](https://grossgym.cl/)
- ✅ **Diseño responsive** mobile-first con Bootstrap 5
- ✅ **Componentes standalone** (sin NgModules - Angular v20)
- ✅ **Formularios reactivos** con validaciones
- ✅ **Navegación** con Angular Router
- ✅ **Integración completa** con API REST
- ✅ **Tema oscuro profesional** con colores corporativos Gross Gym
- ✅ **Efectos y animaciones** premium (hover, fade-in, elevación)
- ✅ **Iconos Bootstrap** y tipografía Google Fonts (Roboto)

### Base de Datos (MySQL 8.0)
- ✅ **7 tablas** relacionadas (tipo_pago, plan, estado, socio, suscripcion, instructor, inbody)
- ✅ **Foreign Keys** y constraints
- ✅ **Datos iniciales** de prueba (32 registros)
- ✅ **Scripts SQL** para crear, eliminar y recrear
- ✅ **Compatibilidad** con JPA entities

---

## 🛠 TECNOLOGÍAS

### Backend

| Tecnología | Versión | Descripción |
|------------|---------|-------------|
| **Java** | 17 | Lenguaje de programación |
| **Spring Boot** | 3.2.0 | Framework principal |
| **Spring Data JPA** | 3.2.0 | Persistencia de datos |
| **Spring MVC** | 3.2.0 | Controladores REST |
| **SpringDoc OpenAPI** | 2.3.0 | Swagger UI / Documentación API |
| **MySQL Connector** | 8.0+ | Driver JDBC para MySQL |
| **Maven** | 3.9+ | Gestión de dependencias |
| **Lombok** | Latest | Reducción de boilerplate |

### Frontend

| Tecnología | Versión | Descripción |
|------------|---------|-------------|
| **Angular** | 20.0+ | Framework frontend |
| **TypeScript** | 5.4+ | Lenguaje tipado |
| **Bootstrap** | 5.3.2 | Framework CSS |
| **Bootstrap Icons** | 1.11.3 | Iconografía |
| **Google Fonts** | Roboto | Tipografía profesional |
| **RxJS** | 7.8+ | Programación reactiva |
| **HTML5 / CSS3** | Latest | Estructura y estilos |

### Infraestructura

| Tecnología | Versión | Descripción |
|------------|---------|-------------|
| **MySQL** | 8.0 | Base de datos relacional |
| **Docker** | Latest | Contenedorización |
| **Docker Compose** | 3.8+ | Orquestación de containers |

---

## 🏗 ARQUITECTURA

### Arquitectura en Capas (Layered Architecture)

```
┌──────────────────────────────────────────────────────────┐
│  CAPA DE PRESENTACIÓN (Frontend)                         │
│  Angular 20 + TypeScript + Bootstrap 5                   │
│  ┌────────────┐ ┌────────────┐ ┌────────────┐          │
│  │ Components │ │  Services  │ │   Models   │          │
│  │  (Views)   │ │   (HTTP)   │ │ (Interfaces)│          │
│  └────────────┘ └────────────┘ └────────────┘          │
└───────────────────────┬──────────────────────────────────┘
                        │ REST API (JSON)
                        │ HTTP Requests/Responses
┌───────────────────────▼──────────────────────────────────┐
│  CAPA DE CONTROLADOR (Backend)                           │
│  Spring MVC - @RestController                            │
│  ┌──────────────────────────────────────────────┐       │
│  │ SocioController, SuscripcionController, etc. │       │
│  │ GET, POST, PUT, DELETE endpoints             │       │
│  └──────────────────────────────────────────────┘       │
└───────────────────────┬──────────────────────────────────┘
                        │
┌───────────────────────▼──────────────────────────────────┐
│  CAPA DE NEGOCIO (Backend)                               │
│  Spring Service - @Service                               │
│  ┌──────────────────────────────────────────────┐       │
│  │ SocioService, SuscripcionService, etc.       │       │
│  │ Lógica de negocio, validaciones, cálculos   │       │
│  └──────────────────────────────────────────────┘       │
└───────────────────────┬──────────────────────────────────┘
                        │
┌───────────────────────▼──────────────────────────────────┐
│  CAPA DE PERSISTENCIA (Backend)                          │
│  Spring Data JPA - @Repository                           │
│  ┌──────────────────────────────────────────────┐       │
│  │ SocioRepository, SuscripcionRepository, etc. │       │
│  │ extends JpaRepository<Entity, ID>            │       │
│  └──────────────────────────────────────────────┘       │
└───────────────────────┬──────────────────────────────────┘
                        │ JDBC / Hibernate
┌───────────────────────▼──────────────────────────────────┐
│  BASE DE DATOS                                           │
│  MySQL 8.0                                               │
│  ┌──────────────────────────────────────────────┐       │
│  │ tipo_pago, plan, estado, socio,              │       │
│  │ suscripcion, instructor, inbody              │       │
│  └──────────────────────────────────────────────┘       │
└──────────────────────────────────────────────────────────┘
```

### Flujo de Datos

```
1. Usuario interactúa con el FRONTEND (Angular)
   ↓
2. Componente Angular llama a un SERVICE
   ↓
3. Service HTTP envía petición REST al BACKEND
   ↓
4. CONTROLLER recibe la petición (@RestController)
   ↓
5. Controller llama al SERVICE (@Service)
   ↓
6. Service ejecuta lógica de negocio y llama al REPOSITORY
   ↓
7. Repository (JPA) ejecuta query en la BASE DE DATOS
   ↓
8. La respuesta viaja de vuelta:
   DB → Repository → Service → Controller → HTTP → Frontend → User
```

---

## 📋 REQUISITOS PREVIOS

### Obligatorios

- ✅ **Java 17** o superior ([Descargar](https://adoptium.net/))
- ✅ **Maven 3.8+** ([Descargar](https://maven.apache.org/download.cgi))
- ✅ **Node.js 18+** ([Descargar](https://nodejs.org/))
- ✅ **Angular CLI 20+** (`npm install -g @angular/cli@20`)
- ✅ **MySQL 8.0+** ([Descargar](https://dev.mysql.com/downloads/))

### Opcionales

- 🐳 **Docker Desktop** (para ejecutar con containers) ([Descargar](https://www.docker.com/products/docker-desktop/))
- 💡 **IDE**: IntelliJ IDEA, VS Code, Eclipse

### Verificar Instalaciones

```bash
java --version      # Java 17+
mvn --version       # Maven 3.8+
node --version      # Node.js 18+
ng version          # Angular CLI 20+
mysql --version     # MySQL 8.0+
docker --version    # Docker (opcional)
```

---

## 📁 ESTRUCTURA DEL PROYECTO

```
crud-suscripcion-gym/
├── src/main/java/com/grossgym/fitness/
│   ├── config/                         # Configuraciones
│   │   ├── CorsConfig.java            # CORS para frontend
│   │   └── OpenApiConfig.java         # Swagger UI
│   ├── controller/                     # REST Controllers
│   │   ├── SocioController.java
│   │   ├── SuscripcionController.java
│   │   ├── PlanController.java
│   │   ├── TipoPagoController.java
│   │   └── EstadoController.java
│   ├── service/                        # Lógica de negocio
│   │   ├── SocioService.java
│   │   ├── SuscripcionService.java
│   │   └── impl/                       # Implementaciones
│   │       ├── SocioServiceImpl.java
│   │       └── ...
│   ├── repository/                     # JPA Repositories
│   │   ├── SocioRepository.java
│   │   ├── SuscripcionRepository.java
│   │   └── ...
│   ├── model/                          # Entidades JPA
│   │   ├── Socio.java
│   │   ├── Suscripcion.java
│   │   ├── Plan.java
│   │   └── ...
│   └── GrossGymFitnessApplication.java # Main class
│
├── src/main/resources/
│   └── application.properties          # Configuración Spring Boot
│
├── frontend/                           # Aplicación Angular
│   ├── src/app/
│   │   ├── components/                 # Componentes UI
│   │   │   ├── socios/
│   │   │   │   ├── socios-list/
│   │   │   │   └── socios-form/
│   │   │   ├── suscripciones/
│   │   │   └── planes/
│   │   ├── services/                   # Servicios HTTP
│   │   │   ├── socio.service.ts
│   │   │   ├── suscripcion.service.ts
│   │   │   └── ...
│   │   ├── models/                     # Interfaces TypeScript
│   │   │   ├── socio.model.ts
│   │   │   ├── suscripcion.model.ts
│   │   │   └── ...
│   │   ├── app.component.ts            # Componente raíz
│   │   └── app.routes.ts               # Rutas
│   ├── src/styles.css                  # Estilos globales
│   ├── src/gym-theme.css               # Tema Gross Gym
│   └── src/index.html                  # HTML principal
│
├── scripts/                            # Scripts SQL
│   ├── create-tables.sql               # Crear tablas
│   ├── drop-database.sql               # Eliminar tablas
│   ├── recreate-database.sql           # Recrear BD completa
│   ├── init.sql                        # Datos iniciales
│   └── recreate-db.sh                  # Script bash
│
├── docs/                               # Documentación
│   ├── 01-INICIO-RAPIDO.md
│   ├── 02-INSTALACION.md
│   ├── 03-DOCKER.md
│   ├── 04-BASE-DATOS.md
│   ├── 05-SWAGGER-UI.md
│   ├── 06-TEMA-GROSSGYM.md
│   └── 07-SOLUCIONES-COMUNES.md
│
├── Dockerfile                          # Imagen Docker del backend
├── docker-compose.yml                  # Orquestación Docker
├── pom.xml                             # Dependencias Maven
├── README.md                           # ← Este archivo
└── DOCUMENTACION.md                    # Documentación técnica completa
```

---

## 🌐 API ENDPOINTS

### Base URL: `http://localhost:8080/api`

### Socios

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/socios` | Listar todos los socios |
| GET | `/socios/{rut}` | Obtener socio por RUT |
| POST | `/socios` | Crear nuevo socio |
| PUT | `/socios/{rut}` | Actualizar socio |
| DELETE | `/socios/{rut}` | Eliminar socio |
| GET | `/socios/habilitado/{estado}` | Filtrar por estado habilitado |
| GET | `/socios/buscar?nombre=X` | Buscar por nombre |

### Suscripciones

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/suscripciones` | Listar todas las suscripciones |
| GET | `/suscripciones/{id}` | Obtener suscripción por ID |
| POST | `/suscripciones` | Crear nueva suscripción |
| PUT | `/suscripciones/{id}` | Actualizar suscripción |
| DELETE | `/suscripciones/{id}` | Eliminar suscripción |
| GET | `/suscripciones/vigentes` | Suscripciones activas |
| GET | `/suscripciones/transaccion/{nro}` | Buscar por nro de transacción |

### Planes

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/planes` | Listar todos los planes |
| GET | `/planes/{id}` | Obtener plan por ID |
| POST | `/planes` | Crear nuevo plan |
| PUT | `/planes/{id}` | Actualizar plan |
| DELETE | `/planes/{id}` | Eliminar plan |

### Tipos de Pago

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/tipos-pago` | Listar todos los tipos de pago |
| GET | `/tipos-pago/{id}` | Obtener tipo de pago por ID |
| POST | `/tipos-pago` | Crear nuevo tipo de pago |
| PUT | `/tipos-pago/{id}` | Actualizar tipo de pago |
| DELETE | `/tipos-pago/{id}` | Eliminar tipo de pago |
| GET | `/tipos-pago/activos` | Tipos de pago activos |

### Estados

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/estados` | Listar todos los estados |
| GET | `/estados/{id}` | Obtener estado por ID |
| POST | `/estados` | Crear nuevo estado |
| PUT | `/estados/{id}` | Actualizar estado |
| DELETE | `/estados/{id}` | Eliminar estado |
| GET | `/estados/habilitados` | Estados habilitados |

**Total: 31+ endpoints**

### 📖 Documentación Interactiva con Swagger UI

```
http://localhost:8080/api/swagger-ui/index.html
```

Swagger UI te permite:
- ✅ Ver todos los endpoints
- ✅ Probar requests directamente desde el navegador
- ✅ Ver schemas de request/response
- ✅ Exportar documentación OpenAPI

---

## 🐳 DOCKER

### Opción 1: Solo Backend en Docker (Recomendado)

```bash
# Construir imagen
docker build -t grossgym-backend:latest .

# Ejecutar container (conectándose a MySQL local)
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

# Ver logs
docker logs -f grossgym-backend
```

### Opción 2: Con Docker Compose (Completo)

```bash
# Iniciar todo (backend + dependencias)
docker-compose up -d

# Ver logs
docker-compose logs -f

# Detener todo
docker-compose down
```

**Para más detalles:** [`docs/03-DOCKER.md`](docs/03-DOCKER.md)

---

## 📸 SCREENSHOTS

### Frontend - Lista de Planes
```
┌──────────────────────────────────────────────────────────┐
│ 🏋️ GROSS GYM FITNESS              SOCIOS PLANES SUSCR. │
├──────────────────────────────────────────────────────────┤
│           SISTEMA DE GESTIÓN DE SUSCRIPCIONES            │
├──────────────────────────────────────────────────────────┤
│                                                           │
│  ┌───────────┐ ┌───────────┐ ┌───────────┐             │
│  │ PLAN      │ │ PLAN      │ │ PLAN      │             │
│  │ MENSUAL   │ │ TRIMESTRAL│ │ ANUAL     │             │
│  │           │ │           │ │           │             │
│  │ $35,000   │ │ $90,000   │ │ $300,000  │             │
│  │           │ │           │ │           │             │
│  │ 📅 1 MES  │ │ 📅 3 MESES│ │ 📅 12 MESES│             │
│  │ 🏆 $15K   │ │ 🏆 $10K   │ │ 🏆 GRATIS │             │
│  └───────────┘ └───────────┘ └───────────┘             │
│                                                           │
└──────────────────────────────────────────────────────────┘
```

### Swagger UI - Documentación Interactiva
```
http://localhost:8080/api/swagger-ui/index.html

┌────────────────────────────────────────────────┐
│ Gross Gym Fitness - API Documentation         │
│ Version: 1.0.0                                 │
├────────────────────────────────────────────────┤
│ 🔽 Socios - 7 endpoints                       │
│ 🔽 Suscripciones - 7 endpoints                │
│ 🔽 Planes - 5 endpoints                       │
│ 🔽 Tipos de Pago - 6 endpoints                │
│ 🔽 Estados - 6 endpoints                      │
├────────────────────────────────────────────────┤
│ [Try it out] Probar cada endpoint             │
└────────────────────────────────────────────────┘
```

---

## 🎨 TEMA GROSS GYM

El frontend está inspirado en el diseño de [Gross Gym](https://grossgym.cl/):

- 🖤 **Colores oscuros profesionales** (Negro #1a1a1a, Gris #2d2d2d)
- 🧡 **Naranja vibrante** como color de acento (#ff6b35)
- ✨ **Efectos premium** (gradientes, sombras, hover, animaciones)
- 🎯 **Tipografía Roboto** (Google Fonts)
- 🔥 **Bootstrap Icons** integrados
- 📱 **Responsive** mobile-first

**Para personalizar:** [`docs/06-TEMA-GROSSGYM.md`](docs/06-TEMA-GROSSGYM.md)

---

## 🤝 CONTRIBUCIÓN

### Cómo Contribuir

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -m 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abrir un Pull Request

### Estándares de Código

- **Backend:** Seguir convenciones de Java y Spring Boot
- **Frontend:** Seguir guía de estilos de Angular
- **Commits:** Mensajes claros y descriptivos
- **Documentación:** Actualizar README y docs/ si es necesario

---

## 📄 LICENCIA

Este proyecto está bajo la Licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

---

## 👨‍💻 AUTOR

**Juan Daniel MQ**

- Email: contacto@grossgym.cl (ejemplo)
- GitHub: [@juandanielmq](https://github.com/juandanielmq)

---

## 🙏 AGRADECIMIENTOS

- Inspiración de diseño: [Gross Gym](https://grossgym.cl/)
- Framework backend: [Spring Boot](https://spring.io/projects/spring-boot)
- Framework frontend: [Angular](https://angular.io/)
- Documentación API: [SpringDoc OpenAPI](https://springdoc.org/)

---

## 📞 SOPORTE

¿Tienes problemas? Consulta:

1. [`docs/07-SOLUCIONES-COMUNES.md`](docs/07-SOLUCIONES-COMUNES.md) - Troubleshooting
2. [`docs/01-INICIO-RAPIDO.md`](docs/01-INICIO-RAPIDO.md) - Inicio rápido
3. [`DOCUMENTACION.md`](DOCUMENTACION.md) - Documentación técnica completa

---

## 🔗 ENLACES ÚTILES

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Angular Documentation](https://angular.io/docs)
- [Bootstrap Documentation](https://getbootstrap.com/docs/)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [Docker Documentation](https://docs.docker.com/)

---

## ✅ ESTADO DEL PROYECTO

- [x] Backend completo (Spring Boot 3 + Java 17)
- [x] Frontend completo (Angular 20)
- [x] Base de datos (MySQL 8.0)
- [x] API REST documentada (Swagger UI)
- [x] Dockerización
- [x] Tema Gross Gym
- [x] Documentación completa
- [x] **Tests unitarios (JUnit 5 + Mockito - 35+ tests)** ✨
- [x] **Logging empresarial (SLF4J + Logback)** ✨
- [ ] Tests E2E (TODO)
- [ ] CI/CD (TODO)
- [ ] Deploy a producción (TODO)

---

<div align="center">

**⭐ Si te gusta este proyecto, dale una estrella en GitHub ⭐**

**🏋️ Hecho con ❤️ para Gross Gym Fitness**

[⬆ Volver arriba](#-gross-gym-fitness---sistema-de-gestión-de-suscripciones)

</div>
