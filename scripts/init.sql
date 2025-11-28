-- Script de inicialización de base de datos
-- Gross Gym Fitness

-- Crear base de datos si no existe
CREATE DATABASE IF NOT EXISTS db_grossgym_fitness
CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;

USE db_grossgym_fitness;

-- Datos iniciales para Tipos de Pago
INSERT INTO tipo_pago (descripcion, estado) VALUES
('Efectivo', true),
('Tarjeta de Débito', true),
('Tarjeta de Crédito', true),
('Transferencia Bancaria', true),
('WebPay', true),
('Mercado Pago', true)
ON DUPLICATE KEY UPDATE descripcion = VALUES(descripcion);

-- Datos iniciales para Planes
INSERT INTO plan (tipo_plan, monto_plan, duracion, unidad, isMatricula, monto_matricula) VALUES
('Plan Diario', 5000, 1, 'DIA', false, 0),
('Plan Semanal', 25000, 7, 'DIA', false, 0),
('Plan Mensual', 35000, 1, 'MES', true, 15000),
('Plan Trimestral', 90000, 3, 'MES', true, 20000),
('Plan Semestral', 160000, 6, 'MES', true, 25000),
('Plan Anual', 300000, 1, 'AÑO', true, 30000),
('Plan Estudiante', 28000, 1, 'MES', true, 10000),
('Plan Tercera Edad', 25000, 1, 'MES', true, 10000)
ON DUPLICATE KEY UPDATE tipo_plan = VALUES(tipo_plan);

-- Datos iniciales para Estados
INSERT INTO estado (descripcion, habilitado) VALUES
('Activo', true),
('Vencido', true),
('Suspendido', true),
('Cancelado', true),
('Pendiente de Pago', true),
('En Mora', true)
ON DUPLICATE KEY UPDATE descripcion = VALUES(descripcion);

-- Datos iniciales para Instructores
INSERT INTO instructor (nombre_instructor, habilitado) VALUES
('Carlos Pérez Soto', true),
('María González Vargas', true),
('Luis Rodríguez Mora', true),
('Ana Martínez López', true),
('Pedro Silva Rojas', true)
ON DUPLICATE KEY UPDATE nombre_instructor = VALUES(nombre_instructor);

-- Datos de ejemplo para Socios (opcional, para testing)
INSERT INTO socio (rut, nombres, apellido_paterno, apellido_materno, genero, correo, celular, habilitado) VALUES
('12345678-9', 'Juan Pablo', 'Soto', 'Vargas', 'Masculino', 'juan.soto@example.com', '+56912345678', true),
('98765432-1', 'María José', 'González', 'Pérez', 'Femenino', 'maria.gonzalez@example.com', '+56987654321', true),
('11223344-5', 'Pedro Antonio', 'Ramírez', 'López', 'Masculino', 'pedro.ramirez@example.com', '+56911223344', true),
('55667788-K', 'Ana Carolina', 'Martínez', 'Rojas', 'Femenino', 'ana.martinez@example.com', '+56955667788', true),
('99887766-4', 'Luis Fernando', 'Torres', 'Silva', 'Masculino', 'luis.torres@example.com', '+56999887766', true)
ON DUPLICATE KEY UPDATE nombres = VALUES(nombres);

COMMIT;

-- Mensaje de confirmación
SELECT 'Base de datos inicializada correctamente' AS Mensaje;
SELECT COUNT(*) AS 'Total Tipos de Pago' FROM tipo_pago;
SELECT COUNT(*) AS 'Total Planes' FROM plan;
SELECT COUNT(*) AS 'Total Estados' FROM estado;
SELECT COUNT(*) AS 'Total Instructores' FROM instructor;
SELECT COUNT(*) AS 'Total Socios' FROM socio;

