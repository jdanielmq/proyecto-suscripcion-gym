#!/bin/bash

# Script para recrear la base de datos completa
# Gross Gym Fitness

# Colores
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

echo "======================================"
echo "🔥 RECREAR BASE DE DATOS"
echo "======================================"
echo ""
echo -e "${RED}⚠️  ADVERTENCIA: Este script eliminará TODOS los datos${NC}"
echo ""
echo "Este script hará:"
echo "  1. Eliminar todas las tablas existentes"
echo "  2. Recrear todas las tablas con tipos compatibles"
echo "  3. Cargar datos iniciales"
echo ""
echo -e "${YELLOW}Los datos actuales se perderán permanentemente${NC}"
echo ""

read -p "¿Estás seguro de continuar? (escribe 'SI' en mayúsculas): " confirmacion

if [ "$confirmacion" != "SI" ]; then
    echo "Operación cancelada."
    exit 0
fi

echo ""
echo "Ejecutando recreación de base de datos..."
echo ""

# Ejecutar script de recreación
mysql -u jdanielmq -pJdmQ1481 db_grossgym_fitness < scripts/recreate-database.sql

if [ $? -eq 0 ]; then
    echo ""
    echo -e "${GREEN}✅ Base de datos recreada exitosamente${NC}"
    echo ""
    echo "Próximos pasos:"
    echo "  1. Iniciar backend: mvn spring-boot:run"
    echo "  2. Iniciar frontend: cd frontend && ng serve"
    echo "  3. Abrir navegador: http://localhost:4200"
else
    echo ""
    echo -e "${RED}❌ Error al recrear la base de datos${NC}"
    echo "Verifica las credenciales y que MySQL esté corriendo"
    exit 1
fi

