# 03 - SWAGGER UI - Documentación Interactiva

## 🎯 ACCEDER A SWAGGER UI

### URL Correcta:

```
http://localhost:8080/api/swagger-ui/index.html
```

**Nota:** Con `/index.html` al final.

---

## 🚀 3 PASOS PARA USAR SWAGGER

### Paso 1: Iniciar el Backend

```bash
cd /Users/juandanielmq/workspace-spring-boot-v3/crud-suscripcion-gym
mvn spring-boot:run
```

**⏱️ Espera hasta ver:**
```
Started GrossGymFitnessApplication in X.XXX seconds
```

### Paso 2: Abrir Swagger UI

```
http://localhost:8080/api/swagger-ui/index.html
```

### Paso 3: ¡Explorar y Probar!

Verás **31+ endpoints** organizados por categorías.

---

## 🌐 TODAS LAS URLs DISPONIBLES

| URL | Descripción |
|-----|-------------|
| `http://localhost:8080/api/swagger-ui/index.html` | ✅ Swagger UI Principal |
| `http://localhost:8080/api/swagger-ui.html` | Alternativa (también funciona) |
| `http://localhost:8080/api/api-docs` | OpenAPI JSON |
| `http://localhost:8080/api/api-docs.yaml` | OpenAPI YAML |

---

## 📋 QUÉ VERÁS EN SWAGGER UI

### Pantalla Principal

```
┌────────────────────────────────────────────────┐
│ Gross Gym Fitness - API de Gestión de         │
│ Suscripciones                                  │
│                                                │
│ Version: 1.0.0                                 │
│ Base URL: http://localhost:8080/api           │
└────────────────────────────────────────────────┘

🔽 Estados - 6 endpoints
  GET    /api/estados
  POST   /api/estados
  GET    /api/estados/{id}
  PUT    /api/estados/{id}
  DELETE /api/estados/{id}
  GET    /api/estados/habilitados

🔽 Planes - 5 endpoints
  GET    /api/planes
  POST   /api/planes
  GET    /api/planes/{id}
  PUT    /api/planes/{id}
  DELETE /api/planes/{id}

🔽 Socios - 7 endpoints
  GET    /api/socios
  POST   /api/socios
  GET    /api/socios/{rut}
  PUT    /api/socios/{rut}
  DELETE /api/socios/{rut}
  GET    /api/socios/habilitado/{estado}
  GET    /api/socios/buscar

🔽 Suscripciones - 7 endpoints
  GET    /api/suscripciones
  POST   /api/suscripciones
  GET    /api/suscripciones/{id}
  PUT    /api/suscripciones/{id}
  DELETE /api/suscripciones/{id}
  GET    /api/suscripciones/vigentes
  GET    /api/suscripciones/transaccion/{nro}

🔽 Tipos de Pago - 6 endpoints
  GET    /api/tipos-pago
  POST   /api/tipos-pago
  GET    /api/tipos-pago/{id}
  PUT    /api/tipos-pago/{id}
  DELETE /api/tipos-pago/{id}
  GET    /api/tipos-pago/activos
```

**Total: 31+ endpoints**

---

## 🧪 7 PRUEBAS RECOMENDADAS

### 1. Listar Todos los Socios

1. **Click en:** `Socios` → `GET /api/socios`
2. **Click en:** `Try it out`
3. **Click en:** `Execute`

**✅ Resultado:** Lista de 5 socios

### 2. Buscar Socio por RUT

1. **Click en:** `Socios` → `GET /api/socios/{rut}`
2. **Click en:** `Try it out`
3. **Campo rut:** `12345678-9`
4. **Click en:** `Execute`

**✅ Resultado:** Datos de Juan Pablo Soto Vargas

### 3. Crear un Nuevo Socio

1. **Click en:** `Socios` → `POST /api/socios`
2. **Click en:** `Try it out`
3. **Edita el JSON:**

```json
{
  "rut": "44444444-4",
  "nombres": "Test Swagger",
  "apellidoPaterno": "Usuario",
  "apellidoMaterno": "Demo",
  "genero": "Femenino",
  "correo": "test@example.com",
  "celular": "+56922222222",
  "habilitado": true
}
```

4. **Click en:** `Execute`

**✅ Resultado:** Response Code 201 Created

### 4. Listar Todos los Planes

1. **Click en:** `Planes` → `GET /api/planes`
2. **Click en:** `Try it out`
3. **Click en:** `Execute`

**✅ Resultado:** 8 planes (Diario, Mensual, Anual, etc.)

### 5. Listar Tipos de Pago Activos

1. **Click en:** `Tipos de Pago` → `GET /api/tipos-pago/activos`
2. **Click en:** `Try it out`
3. **Click en:** `Execute`

**✅ Resultado:** 6 tipos de pago

### 6. Crear una Suscripción

1. **Click en:** `Suscripciones` → `POST /api/suscripciones`
2. **Click en:** `Try it out`
3. **Edita el JSON:**

```json
{
  "fechaCreacion": "2024-11-26T10:00:00",
  "nroTransaccion": "TRX-SWAGGER-001",
  "tipoPago": { "idPago": 1 },
  "plan": { "idPlan": 3 },
  "montoPlan": 35000,
  "montoMatricula": 15000,
  "nroCuotas": 1,
  "socio": { "rut": "12345678-9" }
}
```

4. **Click en:** `Execute`

**✅ Resultado:** Suscripción creada con `fechaTermino` calculada automáticamente

### 7. Listar Suscripciones Vigentes

1. **Click en:** `Suscripciones` → `GET /api/suscripciones/vigentes`
2. **Click en:** `Try it out`
3. **Click en:** `Execute`

**✅ Resultado:** Suscripciones activas

---

## 📊 CÓMO USAR SWAGGER UI

### Cada Endpoint Muestra:

1. **Descripción:** Qué hace el endpoint
2. **Parámetros:** Qué datos necesita
3. **Request Body:** Estructura JSON esperada (POST/PUT)
4. **Responses:** Códigos de respuesta (200, 201, 404, etc.)
5. **Try it out:** Botón para probar

### Pasos para Probar un Endpoint:

1. **Expandir** el endpoint (click en él)
2. **Click** en "Try it out"
3. **Editar** parámetros o body si es necesario
4. **Click** en "Execute"
5. **Ver** el resultado en tiempo real

---

## 📖 SCHEMAS (Modelos de Datos)

Al final de la página Swagger, verás todos los modelos:

### Socio
```json
{
  "rut": "string",
  "nombres": "string",
  "apellidoPaterno": "string",
  "apellidoMaterno": "string",
  "genero": "string",
  "correo": "string",
  "celular": "string",
  "habilitado": "boolean"
}
```

### Suscripcion
```json
{
  "idSuscripcion": "integer",
  "fechaCreacion": "datetime",
  "nroTransaccion": "string",
  "tipoPago": { "idPago": "integer" },
  "plan": { "idPlan": "integer" },
  "montoPlan": "integer",
  "montoMatricula": "integer",
  "nroCuotas": "integer",
  "fechaTermino": "datetime",
  "estado": { "idEstado": "integer" },
  "socio": { "rut": "string" }
}
```

---

## 💡 CARACTERÍSTICAS DE SWAGGER UI

### Para Desarrolladores:
- ✅ Documentación automática
- ✅ Siempre actualizada con el código
- ✅ Pruebas sin Postman
- ✅ Validación de contratos API

### Para Frontend:
- ✅ Ver estructura exacta de requests/responses
- ✅ Conocer todos los endpoints disponibles
- ✅ Probar antes de integrar
- ✅ Ejemplos claros de uso

### Para QA/Testing:
- ✅ Probar todos los casos de uso
- ✅ Validar respuestas
- ✅ Generar casos de prueba

---

## ⚙️ CONFIGURACIÓN ACTUAL

### En `pom.xml`:
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>
```

### En `application.properties`:
```properties
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui
springdoc.swagger-ui.operationsSorter=method
springdoc.swagger-ui.tagsSorter=alpha
springdoc.swagger-ui.tryItOutEnabled=true
springdoc.swagger-ui.enabled=true
```

---

## 🔍 TROUBLESHOOTING

### Problema: Swagger UI no carga

**Soluciones:**

1. Verifica que el backend esté corriendo:
```bash
curl http://localhost:8080/api/socios
```

2. Verifica la URL correcta:
```
http://localhost:8080/api/swagger-ui/index.html
```

3. Revisa los logs del backend

### Problema: Error 404

**Causa:** URL incorrecta

**Solución:** Usa `/index.html` al final:
```
http://localhost:8080/api/swagger-ui/index.html
```

---

## 📥 EXPORTAR DOCUMENTACIÓN

### Descargar OpenAPI JSON:
```bash
curl http://localhost:8080/api/api-docs > openapi.json
```

### Usar con Postman:
1. Abrir Postman
2. Import → OpenAPI 3.0
3. Seleccionar archivo `openapi.json`
4. ✅ Todos los endpoints importados

---

## ✅ CHECKLIST

- [ ] Backend iniciado: `mvn spring-boot:run`
- [ ] Abrí: http://localhost:8080/api/swagger-ui/index.html
- [ ] Swagger UI cargó correctamente
- [ ] Probé GET /api/socios
- [ ] Vi la lista de socios
- [ ] Exploré otros endpoints
- [ ] Revisé los Schemas al final

---

## 🎯 RESUMEN

### URLs Importantes:
- **Swagger UI:** http://localhost:8080/api/swagger-ui/index.html
- **OpenAPI JSON:** http://localhost:8080/api/api-docs

### Comandos:
```bash
# Iniciar backend
mvn spring-boot:run

# Verificar API
curl http://localhost:8080/api/socios
```

### Endpoints Disponibles:
- ✅ Estados: 6 endpoints
- ✅ Planes: 5 endpoints
- ✅ Socios: 7 endpoints
- ✅ Suscripciones: 7 endpoints
- ✅ Tipos de Pago: 6 endpoints

**Total: 31+ endpoints documentados**

---

## 🚀 SIGUIENTE PASO

**Si tienes problemas, lee:**

```bash
cat 04-SOLUCIONES.md
```

**Para ejecutar todo el proyecto:**

```bash
cat 02-EJECUTAR-PROYECTO.md
```

---

**¡Disfruta probando tu API con Swagger UI! 🎉**

