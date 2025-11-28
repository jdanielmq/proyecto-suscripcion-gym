# ⚡ Inicio Rápido - Gross Gym Fitness

> **¿Primera vez aquí?** Esta guía te pondrá en marcha en **5 minutos**.

---

## 🎯 OBJETIVO

Tener el sistema corriendo localmente:
- ✅ Backend en http://localhost:8080
- ✅ Frontend en http://localhost:4200
- ✅ Swagger UI en http://localhost:8080/api/swagger-ui/index.html

---

## 📋 PRE-REQUISITOS

```bash
# Verificar que tengas todo instalado:

java --version      # Java 17+
mvn --version       # Maven 3.8+
node --version      # Node.js 18+
ng version          # Angular CLI 20+
mysql --version     # MySQL 8.0+
```

---

## 🚀 PASOS RÁPIDOS

### 1️⃣ Clonar o Navegar al Proyecto

```bash
cd /Users/juandanielmq/workspace-spring-boot-v3/crud-suscripcion-gym
```

### 2️⃣ Configurar Base de Datos

```bash
# Iniciar MySQL (si no está corriendo)
# macOS: mysql.server start
# Windows: net start MySQL80

# Ejecutar script de creación
mysql -u jdanielmq -p < scripts/recreate-database.sql
```

### 3️⃣ Iniciar Backend

```bash
# Opción A: Con Maven
mvn spring-boot:run

# Opción B: Con JAR compilado
mvn clean package -DskipTests
java -jar target/*.jar
```

**Esperar a ver:**
```
Started GrossGymFitnessApplication in X seconds
```

### 4️⃣ Iniciar Frontend

```bash
# En otra terminal
cd frontend
npm install    # Solo la primera vez
ng serve
```

**Esperar a ver:**
```
✔ Compiled successfully.
```

### 5️⃣ Verificar que Todo Funciona

```bash
# Abrir en tu navegador:

# Frontend
http://localhost:4200

# Backend API
http://localhost:8080/api/planes

# Swagger UI (Documentación)
http://localhost:8080/api/swagger-ui/index.html
```

---

## ✅ CHECKLIST

- [ ] Backend corriendo en puerto 8080
- [ ] Frontend corriendo en puerto 4200
- [ ] Puedo ver la lista de planes en http://localhost:4200/planes
- [ ] Swagger UI carga correctamente

---

## ❌ ¿PROBLEMAS?

### Backend no inicia

```bash
# Ver el error completo
mvn spring-boot:run

# Errores comunes:
# - Puerto 8080 ocupado → Cerrar otra aplicación o cambiar puerto
# - BD no conecta → Verificar que MySQL esté corriendo
```

### Frontend no inicia

```bash
# Reinstalar dependencias
cd frontend
rm -rf node_modules package-lock.json
npm install
ng serve
```

### No se ven datos

```bash
# Verificar que las tablas tengan datos
mysql -u jdanielmq -p db_grossgym_fitness

mysql> SELECT * FROM plan;
mysql> SELECT * FROM tipo_pago;
mysql> SELECT * FROM estado;

# Si están vacías, insertar datos de prueba:
mysql> source scripts/init.sql;
```

---

## 📖 SIGUIENTE PASO

Ahora que todo funciona, explora:

1. **Documentación completa:** `docs/02-INSTALACION.md`
2. **Docker:** `docs/03-DOCKER.md`
3. **Base de datos:** `docs/04-BASE-DATOS.md`
4. **Swagger UI:** `docs/05-SWAGGER-UI.md`
5. **Tema visual:** `docs/06-TEMA-GROSSGYM.md`

---

## 🎉 ¡LISTO!

Si llegaste hasta aquí, tienes el sistema completo funcionando.

**Próximos pasos:**
- Crear un socio
- Crear un plan
- Crear una suscripción
- Ver Swagger UI
- Explorar el código

---

**¿Necesitas ayuda?** Consulta `docs/07-SOLUCIONES-COMUNES.md`

