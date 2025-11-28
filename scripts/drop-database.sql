-- =====================================================
-- Script para ELIMINAR todas las tablas
-- Gross Gym Fitness
-- 
-- ADVERTENCIA: Este script eliminará TODOS los datos
-- Ejecutar como: mysql -u jdanielmq -pJdmQ1481 db_grossgym_fitness < scripts/drop-database.sql
-- =====================================================

USE db_grossgym_fitness;

-- Deshabilitar verificación de foreign keys temporalmente
SET FOREIGN_KEY_CHECKS = 0;

-- Eliminar tablas en orden (sin importar el orden gracias a FOREIGN_KEY_CHECKS=0)
DROP TABLE IF EXISTS inbody;
DROP TABLE IF EXISTS suscripcion;
DROP TABLE IF EXISTS instructor;
DROP TABLE IF EXISTS socio;
DROP TABLE IF EXISTS estado;
DROP TABLE IF EXISTS plan;
DROP TABLE IF EXISTS tipo_pago;

-- Rehabilitar verificación de foreign keys
SET FOREIGN_KEY_CHECKS = 1;

-- Verificar que no queden tablas
SELECT 'Tablas eliminadas correctamente' AS Mensaje;
SHOW TABLES;

