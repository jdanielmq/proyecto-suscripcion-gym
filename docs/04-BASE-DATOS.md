# 05 - GESTIÓN DE BASE DE DATOS

## 🎯 INFORMACIÓN IMPORTANTE

### ⚠️ CONFIGURACIÓN ACTUAL

- ✅ MySQL **siempre está corriendo**
- ✅ Usuario `jdanielmq` y contraseña **ya existen**
- ✅ Base de datos `db_grossgym_fitness` **ya está creada**
- ✅ 7 tablas con 32 registros iniciales **ya están cargados**

**🚫 NO NECESITAS:**
- Iniciar/detener MySQL
- Crear el usuario
- Cambiar la contraseña

**⚠️ IMPORTANTE:** No cambiar credenciales (afectan otros sistemas)

---

## 📊 ESTADO ACTUAL DE LA BASE DE DATOS

### Base de Datos:
```
Nombre: db_grossgym_fitness
Engine: InnoDB
Charset: utf8mb4
```

### Tablas (7):
1. `tipo_pago` - 6 registros
2. `plan` - 8 registros
3. `estado` - 6 registros
4. `socio` - 5 registros
5. `suscripcion` - 0 registros (se crearán desde la app)
6. `instructor` - 5 registros
7. `inbody` - 0 registros (se crearán desde la app)

**Total:** 32 registros iniciales

---

## 🔍 VERIFICAR BASE DE DATOS

### 1. MySQL está corriendo:

```bash
mysql -u jdanielmq -pJdmQ1481 -e "SELECT 1"
```

**✅ Si ves:** `1` → MySQL OK

### 2. Base de datos existe:

```bash
mysql -u jdanielmq -pJdmQ1481 -e "SHOW DATABASES LIKE 'db_grossgym_fitness'"
```

**✅ Si ves:** `db_grossgym_fitness` → BD OK

### 3. Ver tablas:

```bash
mysql -u jdanielmq -pJdmQ1481 db_grossgym_fitness -e "SHOW TABLES;"
```

**✅ Deberías ver:** 7 tablas

### 4. Contar registros:

```bash
mysql -u jdanielmq -pJdmQ1481 db_grossgym_fitness -e "
SELECT 'tipo_pago' as tabla, COUNT(*) as registros FROM tipo_pago
UNION ALL SELECT 'plan', COUNT(*) FROM plan
UNION ALL SELECT 'estado', COUNT(*) FROM estado
UNION ALL SELECT 'socio', COUNT(*) FROM socio
UNION ALL SELECT 'instructor', COUNT(*) FROM instructor;"
```

**✅ Deberías ver:** 32 registros en total

---

## 🔧 RECREAR BASE DE DATOS

### ⚠️ CUÁNDO HACERLO

Solo recrear si:
- ❌ Hay errores de compatibilidad de tipos
- ❌ Las foreign keys fallan
- ❌ Los datos están corruptos
- ❌ Necesitas empezar de cero

### 📋 OPCIÓN 1: Script Automático

```bash
cd /Users/juandanielmq/workspace-spring-boot-v3/crud-suscripcion-gym

# Ejecutar script (incluye DROP y CREATE)
mysql -u jdanielmq -pJdmQ1481 db_grossgym_fitness < scripts/recreate-database.sql
```

### 📋 OPCIÓN 2: Paso a Paso

#### Paso 1: Eliminar Tablas

```bash
mysql -u jdanielmq -pJdmQ1481 db_grossgym_fitness < scripts/drop-database.sql
```

#### Paso 2: Crear Tablas

```bash
mysql -u jdanielmq -pJdmQ1481 db_grossgym_fitness < scripts/create-tables.sql
```

#### Paso 3: Cargar Datos Iniciales

```bash
mysql -u jdanielmq -pJdmQ1481 db_grossgym_fitness < scripts/init.sql
```

### 📋 OPCIÓN 3: Script Bash con Confirmación

```bash
cd /Users/juandanielmq/workspace-spring-boot-v3/crud-suscripcion-gym

# Ejecutar script interactivo
./scripts/recreate-db.sh
```

Te pedirá confirmación antes de eliminar las tablas.

---

## 📝 ESTRUCTURA DE LAS TABLAS

### 1. tipo_pago
```sql
CREATE TABLE tipo_pago (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(40) NOT NULL,
    estado TINYINT(1) DEFAULT 1
);
```

**Datos iniciales:** Efectivo, Débito, Crédito, Transferencia, WebPay, Mercado Pago

### 2. plan
```sql
CREATE TABLE plan (
    id_plan INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(30) NOT NULL,
    descripcion VARCHAR(200),
    monto_plan INT NOT NULL,
    monto_matricula INT DEFAULT 0,
    duracion INT NOT NULL,
    unidad_tiempo VARCHAR(10) NOT NULL
);
```

**Datos iniciales:** 8 planes (Diario, Semanal, Mensual, Trimestral, Semestral, Anual, Estudiante, Tercera Edad)

### 3. estado
```sql
CREATE TABLE estado (
    id_estado INT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(20) NOT NULL,
    habilitado TINYINT(1) DEFAULT 1
);
```

**Datos iniciales:** Activo, Vencido, Suspendido, Cancelado, Pendiente de Pago, En Mora

### 4. instructor
```sql
CREATE TABLE instructor (
    id_instructor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    habilitado TINYINT(1) DEFAULT 1
);
```

**Datos iniciales:** 5 instructores

### 5. socio
```sql
CREATE TABLE socio (
    rut VARCHAR(10) PRIMARY KEY,
    nombres VARCHAR(40) NOT NULL,
    apellido_paterno VARCHAR(40) NOT NULL,
    apellido_materno VARCHAR(40) NOT NULL,
    genero VARCHAR(20) NOT NULL,
    correo VARCHAR(100) NOT NULL,
    celular VARCHAR(20) NOT NULL,
    habilitado TINYINT(1) DEFAULT 1
);
```

**Datos iniciales:** 5 socios

### 6. suscripcion
```sql
CREATE TABLE suscripcion (
    id_suscripcion BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nro_transaccion VARCHAR(50) NOT NULL UNIQUE,
    id_pago INT NOT NULL,
    id_plan INT NOT NULL,
    monto_plan INT NOT NULL,
    monto_matricula INT DEFAULT 0,
    nro_cuotas INT DEFAULT 1,
    fecha_termino TIMESTAMP NULL,
    id_estado INT NOT NULL,
    rut VARCHAR(10) NOT NULL,
    FOREIGN KEY (id_pago) REFERENCES tipo_pago(id_pago),
    FOREIGN KEY (id_plan) REFERENCES plan(id_plan),
    FOREIGN KEY (id_estado) REFERENCES estado(id_estado),
    FOREIGN KEY (rut) REFERENCES socio(rut)
);
```

**Datos iniciales:** 0 (se crean desde la app)

### 7. inbody
```sql
CREATE TABLE inbody (
    id_inbody BIGINT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(10) NOT NULL,
    habilitado TINYINT(1) DEFAULT 1,
    json_inbody JSON NOT NULL,
    fecha_evaluacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    comentario VARCHAR(100),
    id_instructor INT,
    FOREIGN KEY (rut) REFERENCES socio(rut),
    FOREIGN KEY (id_instructor) REFERENCES instructor(id_instructor)
);
```

**Datos iniciales:** 0 (se crean desde la app)

---

## 🔄 RESPALDO Y RESTAURACIÓN

### Crear Respaldo:

```bash
mysqldump -u jdanielmq -pJdmQ1481 db_grossgym_fitness > backup_$(date +%Y%m%d_%H%M%S).sql
```

### Restaurar desde Respaldo:

```bash
mysql -u jdanielmq -pJdmQ1481 db_grossgym_fitness < backup_YYYYMMDD_HHMMSS.sql
```

---

## 🔍 CONSULTAS ÚTILES

### Ver Estructura de una Tabla:

```bash
mysql -u jdanielmq -pJdmQ1481 db_grossgym_fitness -e "DESCRIBE socio;"
```

### Ver Foreign Keys:

```bash
mysql -u jdanielmq -pJdmQ1481 db_grossgym_fitness -e "
SELECT 
    TABLE_NAME,
    COLUMN_NAME,
    REFERENCED_TABLE_NAME,
    REFERENCED_COLUMN_NAME
FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE
WHERE TABLE_SCHEMA = 'db_grossgym_fitness'
AND REFERENCED_TABLE_NAME IS NOT NULL;"
```

### Ver Todos los Socios:

```bash
mysql -u jdanielmq -pJdmQ1481 db_grossgym_fitness -e "SELECT * FROM socio;"
```

### Ver Todos los Planes:

```bash
mysql -u jdanielmq -pJdmQ1481 db_grossgym_fitness -e "SELECT * FROM plan;"
```

---

## ⚙️ CONFIGURACIÓN EN EL PROYECTO

### application.properties:

```properties
# Configuración de MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/db_grossgym_fitness?useSSL=false&serverTimezone=UTC
spring.datasource.username=jdanielmq
spring.datasource.password=JdmQ1481

# Hibernate no modifica las tablas
spring.jpa.hibernate.ddl-auto=none
```

**✅ Configuración correcta** - No cambiar

---

## 🚨 PROBLEMAS COMUNES

### Error: Access denied

**Causa:** Credenciales incorrectas

**Solución:** Verificar `application.properties`:
```properties
spring.datasource.username=jdanielmq
spring.datasource.password=JdmQ1481
```

### Error: Unknown database

**Causa:** Base de datos no existe

**Solución:** Recrear la BD:
```bash
mysql -u jdanielmq -pJdmQ1481 < scripts/recreate-database.sql
```

### Error: Table doesn't exist

**Causa:** Tablas no creadas

**Solución:** Ejecutar script de creación:
```bash
mysql -u jdanielmq -pJdmQ1481 db_grossgym_fitness < scripts/create-tables.sql
```

### Error: Foreign key constraint fails

**Causa:** Tipos de datos incompatibles

**Solución:** Recrear toda la BD:
```bash
mysql -u jdanielmq -pJdmQ1481 db_grossgym_fitness < scripts/recreate-database.sql
```

---

## 📊 SCRIPTS DISPONIBLES

| Script | Ubicación | Descripción |
|--------|-----------|-------------|
| `recreate-database.sql` | `scripts/` | Elimina y recrea todo |
| `drop-database.sql` | `scripts/` | Solo elimina tablas |
| `create-tables.sql` | `scripts/` | Solo crea tablas |
| `init.sql` | `scripts/` | Solo datos iniciales |
| `recreate-db.sh` | `scripts/` | Script bash interactivo |

---

## ✅ CHECKLIST

- [x] MySQL corriendo
- [x] Usuario `jdanielmq` existe
- [x] Base de datos `db_grossgym_fitness` creada
- [x] 7 tablas creadas
- [x] 32 registros iniciales cargados
- [x] Foreign keys configuradas
- [x] Tipos de datos compatibles
- [ ] Backend se conecta correctamente

---

## 🎯 RESUMEN

### Credenciales:
```
Usuario: jdanielmq
Password: JdmQ1481
Base de Datos: db_grossgym_fitness
```

### Comandos Rápidos:
```bash
# Verificar MySQL
mysql -u jdanielmq -pJdmQ1481 -e "SELECT 1"

# Ver tablas
mysql -u jdanielmq -pJdmQ1481 db_grossgym_fitness -e "SHOW TABLES;"

# Recrear BD
mysql -u jdanielmq -pJdmQ1481 db_grossgym_fitness < scripts/recreate-database.sql
```

### Scripts:
- ✅ `scripts/recreate-database.sql` - Completo
- ✅ `scripts/drop-database.sql` - Solo DROP
- ✅ `scripts/create-tables.sql` - Solo CREATE
- ✅ `scripts/init.sql` - Solo INSERT

---

## 📚 DOCUMENTOS RELACIONADOS

| Documento | Cuándo Leer |
|-----------|-------------|
| `01-INICIO.md` | Primera vez |
| `02-EJECUTAR-PROYECTO.md` | Ejecutar todo |
| `04-SOLUCIONES.md` | Errores generales |
| `05-BASE-DATOS.md` | ← ESTÁS AQUÍ |

---

**¡La base de datos está lista y funcionando! 🎉**

