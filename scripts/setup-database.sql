-- =====================================================
-- Script de Configuración de Base de Datos
-- Gross Gym Fitness
-- 
-- Este script:
-- 1. Crea el usuario si no existe
-- 2. Crea la base de datos
-- 3. Otorga permisos al usuario
-- =====================================================

-- NOTA: Ejecutar este script como usuario root:
-- mysql -u root -p < scripts/setup-database.sql

-- 1. Crear usuario (si no existe)
CREATE USER IF NOT EXISTS 'jdanielmq'@'localhost' IDENTIFIED BY 'JdmQ1481';

-- 2. Otorgar todos los permisos en todas las bases de datos
GRANT ALL PRIVILEGES ON *.* TO 'jdanielmq'@'localhost' WITH GRANT OPTION;

-- 3. Aplicar cambios
FLUSH PRIVILEGES;

-- 4. Crear la base de datos (si no existe)
CREATE DATABASE IF NOT EXISTS db_grossgym_fitness
CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;

-- 5. Verificar que se creó
SELECT 'Usuario y base de datos configurados correctamente' AS Mensaje;

-- 6. Mostrar bases de datos
SHOW DATABASES LIKE 'db_grossgym%';

-- 7. Verificar permisos del usuario
SHOW GRANTS FOR 'jdanielmq'@'localhost';

