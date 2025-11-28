-- =====================================================
-- Script para CREAR todas las tablas
-- Gross Gym Fitness
-- 
-- Este script crea las tablas con tipos compatibles con JPA
-- Ejecutar como: mysql -u jdanielmq -pJdmQ1481 db_grossgym_fitness < scripts/create-tables.sql
-- =====================================================

USE db_grossgym_fitness;

-- =====================================================
-- Tabla: tipo_pago
-- Descripción: Tipos de pago disponibles (Débito, Crédito, Efectivo, etc.)
-- =====================================================
CREATE TABLE IF NOT EXISTS tipo_pago (
    id_pago INT NOT NULL AUTO_INCREMENT,
    descripcion VARCHAR(200) NOT NULL COMMENT 'Descripción del tipo de pago (débito, crédito, efectivo, etc)',
    estado BOOLEAN NOT NULL DEFAULT TRUE COMMENT 'Estado para habilitar o desactivar',
    PRIMARY KEY (id_pago),
    INDEX idx_estado (estado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- =====================================================
-- Tabla: plan
-- Descripción: Planes de suscripción (Diario, Mensual, Anual, etc.)
-- =====================================================
CREATE TABLE IF NOT EXISTS plan (
    id_plan INT NOT NULL AUTO_INCREMENT,
    tipo_plan VARCHAR(200) NOT NULL COMMENT 'Tipo de plan: Diario, Mensual, etc.',
    monto_plan BIGINT NULL COMMENT 'Precio del plan',
    duracion INT NULL COMMENT 'Duración del plan',
    unidad VARCHAR(100) NOT NULL COMMENT 'Unidad de medida: DIA, MES, AÑO',
    isMatricula BOOLEAN NOT NULL DEFAULT FALSE COMMENT 'Si tiene matrícula',
    monto_matricula BIGINT NOT NULL DEFAULT 0 COMMENT 'Valor de la matrícula',
    PRIMARY KEY (id_plan),
    INDEX idx_tipo_plan (tipo_plan)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- =====================================================
-- Tabla: estado
-- Descripción: Estados de suscripciones (Activo, Vencido, Suspendido, etc.)
-- =====================================================
CREATE TABLE IF NOT EXISTS estado (
    id_estado INT NOT NULL AUTO_INCREMENT,
    descripcion VARCHAR(100) NOT NULL,
    habilitado BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id_estado),
    INDEX idx_habilitado (habilitado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- =====================================================
-- Tabla: instructor
-- Descripción: Instructores del gimnasio
-- =====================================================
CREATE TABLE IF NOT EXISTS instructor (
    id_instructor INT NOT NULL AUTO_INCREMENT,
    nombre_instructor VARCHAR(200) NOT NULL,
    habilitado BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id_instructor),
    INDEX idx_habilitado (habilitado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- =====================================================
-- Tabla: socio
-- Descripción: Socios del gimnasio
-- =====================================================
CREATE TABLE IF NOT EXISTS socio (
    rut VARCHAR(10) NOT NULL,
    nombres VARCHAR(200) NOT NULL COMMENT 'Nombres del socio',
    apellido_paterno VARCHAR(200) NOT NULL,
    apellido_materno VARCHAR(200) NOT NULL,
    genero VARCHAR(100) NOT NULL COMMENT 'Femenino, Masculino, Otro',
    correo VARCHAR(200) NOT NULL,
    celular VARCHAR(100) NOT NULL,
    habilitado BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (rut),
    UNIQUE INDEX idx_correo (correo),
    INDEX idx_habilitado (habilitado),
    INDEX idx_nombres (nombres),
    INDEX idx_apellido_paterno (apellido_paterno)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- =====================================================
-- Tabla: suscripcion
-- Descripción: Suscripciones de los socios
-- =====================================================
CREATE TABLE IF NOT EXISTS suscripcion (
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
    INDEX idx_fecha_creacion (fecha_creacion),
    INDEX idx_fecha_termino (fecha_termino),
    
    -- Foreign Keys
    CONSTRAINT suscripcion_tipo_pago_FK 
        FOREIGN KEY (id_tipo_pago) 
        REFERENCES tipo_pago(id_pago)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    
    CONSTRAINT suscripcion_plan_FK 
        FOREIGN KEY (id_plan) 
        REFERENCES plan(id_plan)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    
    CONSTRAINT suscripcion_estado_FK 
        FOREIGN KEY (id_estado) 
        REFERENCES estado(id_estado)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    
    CONSTRAINT suscripcion_socio_FK 
        FOREIGN KEY (id_socio) 
        REFERENCES socio(rut)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- =====================================================
-- Tabla: inbody
-- Descripción: Evaluaciones InBody de composición corporal
-- =====================================================
CREATE TABLE IF NOT EXISTS inbody (
    id_inbody BIGINT NOT NULL AUTO_INCREMENT,
    id_socio VARCHAR(10) NOT NULL,
    habilitado BOOLEAN NOT NULL DEFAULT TRUE,
    json_inbody JSON NOT NULL COMMENT 'Datos de evaluación en formato JSON',
    fecha_evaluacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    comentario VARCHAR(100) NULL,
    id_instructor INT NULL,
    PRIMARY KEY (id_inbody),
    INDEX idx_fecha_evaluacion (fecha_evaluacion),
    INDEX idx_habilitado (habilitado),
    
    -- Foreign Keys con tipos compatibles
    CONSTRAINT inbody_socio_FK 
        FOREIGN KEY (id_socio) 
        REFERENCES socio(rut)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    
    CONSTRAINT inbody_instructor_FK 
        FOREIGN KEY (id_instructor) 
        REFERENCES instructor(id_instructor)
        ON DELETE SET NULL
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- =====================================================
-- Verificación de tablas creadas
-- =====================================================
SELECT 'Todas las tablas creadas correctamente' AS Mensaje;
SHOW TABLES;

-- Mostrar estructura de cada tabla
SELECT 'Estructura de las tablas:' AS Info;
DESCRIBE tipo_pago;
DESCRIBE plan;
DESCRIBE estado;
DESCRIBE instructor;
DESCRIBE socio;
DESCRIBE suscripcion;
DESCRIBE inbody;

