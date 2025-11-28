-- =====================================================
-- Script COMPLETO para RECREAR la base de datos
-- Gross Gym Fitness
-- 
-- Este script:
-- 1. Elimina todas las tablas
-- 2. Recrea todas las tablas con tipos correctos
-- 3. Carga datos iniciales
-- 
-- ADVERTENCIA: Eliminará TODOS los datos existentes
-- 
-- Ejecutar como:
-- mysql -u jdanielmq -pJdmQ1481 db_grossgym_fitness < scripts/recreate-database.sql
-- =====================================================

USE db_grossgym_fitness;

-- =====================================================
-- PASO 1: ELIMINAR TABLAS EXISTENTES
-- =====================================================

SELECT 'PASO 1: Eliminando tablas existentes...' AS Info;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS inbody;
DROP TABLE IF EXISTS suscripcion;
DROP TABLE IF EXISTS instructor;
DROP TABLE IF EXISTS socio;
DROP TABLE IF EXISTS estado;
DROP TABLE IF EXISTS plan;
DROP TABLE IF EXISTS tipo_pago;

SET FOREIGN_KEY_CHECKS = 1;

SELECT 'Tablas eliminadas correctamente' AS Resultado;

-- =====================================================
-- PASO 2: CREAR TABLAS CON TIPOS COMPATIBLES
-- =====================================================

SELECT 'PASO 2: Creando tablas con tipos compatibles...' AS Info;

-- Tabla: tipo_pago
CREATE TABLE tipo_pago (
    id_pago INT NOT NULL AUTO_INCREMENT,
    descripcion VARCHAR(200) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id_pago),
    INDEX idx_estado (estado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabla: plan
CREATE TABLE plan (
    id_plan INT NOT NULL AUTO_INCREMENT,
    tipo_plan VARCHAR(200) NOT NULL,
    monto_plan BIGINT NULL,
    duracion INT NULL,
    unidad VARCHAR(100) NOT NULL,
    isMatricula BOOLEAN NOT NULL DEFAULT FALSE,
    monto_matricula BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id_plan)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabla: estado
CREATE TABLE estado (
    id_estado INT NOT NULL AUTO_INCREMENT,
    descripcion VARCHAR(100) NOT NULL,
    habilitado BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id_estado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabla: instructor
CREATE TABLE instructor (
    id_instructor INT NOT NULL AUTO_INCREMENT,
    nombre_instructor VARCHAR(200) NOT NULL,
    habilitado BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id_instructor)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabla: socio
CREATE TABLE socio (
    rut VARCHAR(10) NOT NULL,
    nombres VARCHAR(200) NOT NULL,
    apellido_paterno VARCHAR(200) NOT NULL,
    apellido_materno VARCHAR(200) NOT NULL,
    genero VARCHAR(100) NOT NULL,
    correo VARCHAR(200) NOT NULL,
    celular VARCHAR(100) NOT NULL,
    habilitado BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (rut),
    UNIQUE INDEX idx_correo (correo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabla: suscripcion
CREATE TABLE suscripcion (
    id_suscripcion INT NOT NULL AUTO_INCREMENT,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    nro_transaccion VARCHAR(100) NOT NULL,
    id_tipo_pago INT NOT NULL,
    id_plan INT NOT NULL,
    monto_plan BIGINT NULL,
    monto_matricula BIGINT NULL,
    nro_cuotas INT NULL,
    fecha_termino TIMESTAMP NULL,
    id_estado INT NULL,
    id_socio VARCHAR(10) NOT NULL,
    PRIMARY KEY (id_suscripcion),
    UNIQUE INDEX idx_nro_transaccion (nro_transaccion),
    CONSTRAINT suscripcion_tipo_pago_FK FOREIGN KEY (id_tipo_pago) REFERENCES tipo_pago(id_pago),
    CONSTRAINT suscripcion_plan_FK FOREIGN KEY (id_plan) REFERENCES plan(id_plan),
    CONSTRAINT suscripcion_estado_FK FOREIGN KEY (id_estado) REFERENCES estado(id_estado),
    CONSTRAINT suscripcion_socio_FK FOREIGN KEY (id_socio) REFERENCES socio(rut)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabla: inbody (con tipos compatibles)
CREATE TABLE inbody (
    id_inbody BIGINT NOT NULL AUTO_INCREMENT,
    id_socio VARCHAR(10) NOT NULL,
    habilitado BOOLEAN NOT NULL DEFAULT TRUE,
    json_inbody JSON NOT NULL,
    fecha_evaluacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    comentario VARCHAR(100) NULL,
    id_instructor INT NULL,
    PRIMARY KEY (id_inbody),
    CONSTRAINT inbody_socio_FK FOREIGN KEY (id_socio) REFERENCES socio(rut),
    CONSTRAINT inbody_instructor_FK FOREIGN KEY (id_instructor) REFERENCES instructor(id_instructor)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SELECT 'Tablas creadas correctamente' AS Resultado;

-- =====================================================
-- PASO 3: CARGAR DATOS INICIALES
-- =====================================================

SELECT 'PASO 3: Cargando datos iniciales...' AS Info;

-- Tipos de Pago
INSERT INTO tipo_pago (descripcion, estado) VALUES
('Efectivo', true),
('Tarjeta de Débito', true),
('Tarjeta de Crédito', true),
('Transferencia Bancaria', true),
('WebPay', true),
('Mercado Pago', true);

-- Planes
INSERT INTO plan (tipo_plan, monto_plan, duracion, unidad, isMatricula, monto_matricula) VALUES
('Plan Diario', 5000, 1, 'DIA', false, 0),
('Plan Semanal', 25000, 7, 'DIA', false, 0),
('Plan Mensual', 35000, 1, 'MES', true, 15000),
('Plan Trimestral', 90000, 3, 'MES', true, 20000),
('Plan Semestral', 160000, 6, 'MES', true, 25000),
('Plan Anual', 300000, 1, 'AÑO', true, 30000),
('Plan Estudiante', 28000, 1, 'MES', true, 10000),
('Plan Tercera Edad', 25000, 1, 'MES', true, 10000);

-- Estados
INSERT INTO estado (descripcion, habilitado) VALUES
('Activo', true),
('Vencido', true),
('Suspendido', true),
('Cancelado', true),
('Pendiente de Pago', true),
('En Mora', true);

-- Instructores
INSERT INTO instructor (nombre_instructor, habilitado) VALUES
('Carlos Pérez Soto', true),
('María González Vargas', true),
('Luis Rodríguez Mora', true),
('Ana Martínez López', true),
('Pedro Silva Rojas', true);

-- Socios de ejemplo
INSERT INTO socio (rut, nombres, apellido_paterno, apellido_materno, genero, correo, celular, habilitado) VALUES
('12345678-9', 'Juan Pablo', 'Soto', 'Vargas', 'Masculino', 'juan.soto@example.com', '+56912345678', true),
('98765432-1', 'María José', 'González', 'Pérez', 'Femenino', 'maria.gonzalez@example.com', '+56987654321', true),
('11223344-5', 'Pedro Antonio', 'Ramírez', 'López', 'Masculino', 'pedro.ramirez@example.com', '+56911223344', true),
('55667788-K', 'Ana Carolina', 'Martínez', 'Rojas', 'Femenino', 'ana.martinez@example.com', '+56955667788', true),
('99887766-4', 'Luis Fernando', 'Torres', 'Silva', 'Masculino', 'luis.torres@example.com', '+56999887766', true);

SELECT 'Datos iniciales cargados correctamente' AS Resultado;

-- =====================================================
-- VERIFICACIÓN FINAL
-- =====================================================

SELECT 'VERIFICACIÓN FINAL:' AS Info;
SELECT 'Tipos de Pago' as Tabla, COUNT(*) as Cantidad FROM tipo_pago
UNION ALL
SELECT 'Planes', COUNT(*) FROM plan
UNION ALL
SELECT 'Estados', COUNT(*) FROM estado
UNION ALL
SELECT 'Instructores', COUNT(*) FROM instructor
UNION ALL
SELECT 'Socios', COUNT(*) FROM socio;

SELECT '✅ Base de datos recreada exitosamente' AS Mensaje;
SELECT '✅ Tipos de datos compatibles con JPA' AS Compatibilidad;
SELECT '✅ Foreign Keys configuradas correctamente' AS ForeignKeys;

