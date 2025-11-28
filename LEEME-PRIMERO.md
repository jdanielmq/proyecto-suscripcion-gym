# 🏋️ Gross Gym Fitness - LÉEME PRIMERO

> **¡Bienvenido al proyecto!** Este archivo te guiará en 2 minutos.

---

## 🎯 ¿QUÉ ES ESTE PROYECTO?

**Sistema completo de gestión de suscripciones para gimnasios**

- 🖥️ **Backend:** Spring Boot 3 + Java 17 + MySQL
- 🎨 **Frontend:** Angular 18 + Bootstrap 5 + Tema Gross Gym
- 📚 **API:** 31+ endpoints REST documentados con Swagger UI
- 🐳 **Docker:** Listo para contenedores

---

## 🚀 INICIO ULTRA-RÁPIDO (4 comandos)

```bash
# 1. Crear base de datos
mysql -u jdanielmq -p < scripts/recreate-database.sql

# 2. Iniciar backend (Terminal 1)
mvn spring-boot:run

# 3. Iniciar frontend (Terminal 2)
cd frontend && ng serve

# 4. Abrir navegador
# Frontend: http://localhost:4200
# Swagger UI: http://localhost:8080/api/swagger-ui/index.html
```

**¿No funciona?** → Lee [`docs/07-SOLUCIONES-COMUNES.md`](docs/07-SOLUCIONES-COMUNES.md)

---

## 📚 DOCUMENTACIÓN (¿Qué leer?)

### Primera vez aquí
→ **[`docs/01-INICIO-RAPIDO.md`](docs/01-INICIO-RAPIDO.md)** (5 min)

### Instalación completa
→ **[`docs/02-INSTALACION.md`](docs/02-INSTALACION.md)** (15 min)

### Uso con Docker
→ **[`docs/03-DOCKER.md`](docs/03-DOCKER.md)** (20 min)

### ¿Tienes un error?
→ **[`docs/07-SOLUCIONES-COMUNES.md`](docs/07-SOLUCIONES-COMUNES.md)** (Troubleshooting)

### Ver todo
→ **[`docs/00-INDICE.md`](docs/00-INDICE.md)** (Índice completo)

---

## 📁 ARCHIVOS PRINCIPALES

```
crud-suscripcion-gym/
│
├── docs/                          ← TODA LA DOCUMENTACIÓN
│   ├── 00-INDICE.md              ← Índice completo
│   ├── 01-INICIO-RAPIDO.md       ← 5 min para ejecutar
│   ├── 02-INSTALACION.md         ← Instalación completa
│   ├── 03-DOCKER.md              ← Docker paso a paso
│   ├── 04-BASE-DATOS.md          ← MySQL
│   ├── 05-SWAGGER-UI.md          ← API Docs
│   ├── 06-TEMA-GROSSGYM.md       ← Diseño
│   └── 07-SOLUCIONES-COMUNES.md  ← Troubleshooting
│
├── README.md                      ← Documentación general
├── DOCUMENTACION.md               ← Documentación técnica
├── LEEME-PRIMERO.md               ← Este archivo
│
├── Dockerfile                     ← Docker backend
├── docker-compose.yml             ← Orquestación
│
├── src/                           ← Backend (Spring Boot)
├── frontend/                      ← Frontend (Angular)
└── scripts/                       ← SQL scripts
```

---

## ✅ REQUISITOS MÍNIMOS

```bash
java --version      # Java 17+
mvn --version       # Maven 3.8+
node --version      # Node.js 18+
ng version          # Angular CLI 18+
mysql --version     # MySQL 8.0+
```

**¿No tienes algo instalado?** → [`docs/02-INSTALACION.md`](docs/02-INSTALACION.md)

---

## 🌐 URLs IMPORTANTES

| Servicio | URL | Descripción |
|----------|-----|-------------|
| **Frontend** | http://localhost:4200 | Interfaz Angular |
| **Backend API** | http://localhost:8080/api | REST API |
| **Swagger UI** | http://localhost:8080/api/swagger-ui/index.html | Documentación interactiva |

---

## 🔧 COMANDOS ÚTILES

### Backend

```bash
# Compilar
mvn clean package

# Ejecutar
mvn spring-boot:run

# Tests
mvn test
```

### Frontend

```bash
cd frontend

# Instalar dependencias
npm install

# Ejecutar
ng serve

# Build producción
ng build
```

### Docker

```bash
# Construir imagen
docker build -t grossgym-backend:latest .

# Ejecutar container
docker run -d --name grossgym-backend -p 8080:8080 grossgym-backend:latest

# Ver logs
docker logs -f grossgym-backend
```

**Más detalles:** [`docs/03-DOCKER.md`](docs/03-DOCKER.md)

---

## 🎨 TEMA GROSS GYM

El frontend tiene un **diseño moderno inspirado en [Gross Gym](https://grossgym.cl/)**:

- 🖤 Colores oscuros profesionales (Negro #1a1a1a)
- 🧡 Naranja vibrante como acento (#ff6b35)
- ✨ Efectos premium (gradientes, sombras, animaciones)
- 📱 Responsive mobile-first

**Ver más:** [`docs/06-TEMA-GROSSGYM.md`](docs/06-TEMA-GROSSGYM.md)

---

## 📊 CARACTERÍSTICAS

### Backend
- ✅ 31+ endpoints REST
- ✅ Swagger UI / OpenAPI 3
- ✅ JPA/Hibernate + MySQL
- ✅ Arquitectura en capas
- ✅ CORS configurado

### Frontend
- ✅ Angular 18 standalone
- ✅ Bootstrap 5 + tema oscuro
- ✅ Formularios reactivos
- ✅ Routing completo
- ✅ Responsive design

### Base de Datos
- ✅ 7 tablas relacionadas
- ✅ Foreign keys
- ✅ Datos de prueba
- ✅ Scripts SQL

---

## 🆘 PROBLEMAS COMUNES

### Backend no inicia
```bash
# Verificar MySQL
mysql -u jdanielmq -p

# Recrear BD
mysql -u jdanielmq -p < scripts/recreate-database.sql

# Iniciar backend
mvn spring-boot:run
```

### Frontend no inicia
```bash
cd frontend

# Reinstalar dependencias
rm -rf node_modules
npm install

# Iniciar
ng serve
```

### Error CORS
→ Ver [`docs/07-SOLUCIONES-COMUNES.md`](docs/07-SOLUCIONES-COMUNES.md) → Sección "Problemas de CORS"

### Puerto ocupado
```bash
# Ver qué usa el puerto 8080
lsof -i :8080

# Matar proceso
kill -9 [PID]
```

**25+ problemas resueltos:** [`docs/07-SOLUCIONES-COMUNES.md`](docs/07-SOLUCIONES-COMUNES.md)

---

## 📖 SIGUIENTES PASOS

### 1. Primera ejecución
1. Lee [`docs/01-INICIO-RAPIDO.md`](docs/01-INICIO-RAPIDO.md)
2. Ejecuta los 4 comandos de arriba
3. Abre http://localhost:4200

### 2. Explorar el proyecto
1. Ver Swagger UI: http://localhost:8080/api/swagger-ui/index.html
2. Crear un socio
3. Crear un plan
4. Crear una suscripción

### 3. Profundizar
1. Lee [`README.md`](README.md) - Visión general
2. Lee [`DOCUMENTACION.md`](DOCUMENTACION.md) - Técnica completa
3. Explora el código fuente

---

## 🐳 EJECUTAR CON DOCKER

### Opción 1: Solo Backend

```bash
# Construir
docker build -t grossgym-backend:latest .

# Ejecutar
docker run -d \
  --name grossgym-backend \
  -p 8080:8080 \
  -e DB_HOST=host.docker.internal \
  --add-host host.docker.internal:host-gateway \
  grossgym-backend:latest
```

### Opción 2: Docker Compose

```bash
# Iniciar todo
docker-compose up -d

# Ver logs
docker-compose logs -f

# Detener
docker-compose down
```

**Guía completa:** [`docs/03-DOCKER.md`](docs/03-DOCKER.md)

---

## ✅ CHECKLIST

### Antes de empezar
- [ ] Java 17 instalado
- [ ] Maven instalado
- [ ] Node.js instalado
- [ ] Angular CLI instalado
- [ ] MySQL corriendo
- [ ] Puertos 8080 y 4200 libres

### Primer ejecución
- [ ] BD creada
- [ ] Backend iniciado (mvn spring-boot:run)
- [ ] Frontend iniciado (ng serve)
- [ ] http://localhost:4200 funciona
- [ ] Swagger UI funciona

---

## 📞 SOPORTE

### Documentación
- [`docs/00-INDICE.md`](docs/00-INDICE.md) - Índice completo
- [`docs/07-SOLUCIONES-COMUNES.md`](docs/07-SOLUCIONES-COMUNES.md) - Troubleshooting

### Enlaces Útiles
- [Spring Boot Docs](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Angular Docs](https://angular.io/docs)
- [Bootstrap Docs](https://getbootstrap.com/docs/)

---

## 🎯 RESUMEN

### En 4 comandos
```bash
mysql -u jdanielmq -p < scripts/recreate-database.sql
mvn spring-boot:run                     # Terminal 1
cd frontend && ng serve                 # Terminal 2
open http://localhost:4200              # Navegador
```

### En 1 documento
→ **[`docs/01-INICIO-RAPIDO.md`](docs/01-INICIO-RAPIDO.md)**

### Todo el índice
→ **[`docs/00-INDICE.md`](docs/00-INDICE.md)**

---

<div align="center">

**🏋️ Gross Gym Fitness**

**Sistema de Gestión de Suscripciones**

---

**¿Listo?** → [`docs/01-INICIO-RAPIDO.md`](docs/01-INICIO-RAPIDO.md)

**¿Problemas?** → [`docs/07-SOLUCIONES-COMUNES.md`](docs/07-SOLUCIONES-COMUNES.md)

**¿Dudas?** → [`docs/00-INDICE.md`](docs/00-INDICE.md)

---

**⭐ ¡Empieza ahora! ⭐**

</div>

