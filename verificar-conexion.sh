#!/bin/bash

# Script de Verificación de Conexión - Gross Gym Fitness
# Este script verifica todos los componentes necesarios

echo "======================================"
echo "🔍 VERIFICACIÓN DE CONEXIÓN - GROSS GYM FITNESS"
echo "======================================"
echo ""

# Colores para output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Función para verificar comando
check_command() {
    if command -v $1 &> /dev/null; then
        echo -e "${GREEN}✅ $1 instalado${NC}"
        $1 --version 2>&1 | head -n 1
    else
        echo -e "${RED}❌ $1 NO instalado${NC}"
        return 1
    fi
    echo ""
}

# 1. Verificar Java
echo "1️⃣  Verificando Java..."
check_command java

# 2. Verificar Maven
echo "2️⃣  Verificando Maven..."
check_command mvn

# 3. Verificar Node.js
echo "3️⃣  Verificando Node.js..."
check_command node

# 4. Verificar npm
echo "4️⃣  Verificando npm..."
check_command npm

# 5. Verificar MySQL
echo "5️⃣  Verificando MySQL..."
echo -e "${GREEN}✅ MySQL debería estar corriendo (compartido con otros sistemas)${NC}"

# Verificar puerto 3306
if lsof -i :3306 > /dev/null 2>&1; then
    echo -e "${GREEN}✅ Puerto 3306 está abierto${NC}"
else
    echo -e "${YELLOW}⚠️  Puerto 3306 no parece estar disponible${NC}"
    echo "   Si MySQL está en otro servidor, esto es normal"
fi
echo ""

# 6. Probar conexión a MySQL con credenciales del proyecto
echo "6️⃣  Probando conexión a MySQL..."
echo "   Usuario: jdanielmq (ya configurado)"
echo "   Base de datos: db_grossgym_fitness"
echo ""

# Probar conexión
mysql -u jdanielmq -pJdmQ1481 -e "SELECT 1;" 2>/dev/null

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Credenciales correctas - Conexión exitosa${NC}"
    
    # Verificar si la base de datos existe
    DB_EXISTS=$(mysql -u jdanielmq -pJdmQ1481 -e "SHOW DATABASES LIKE 'db_grossgym_fitness';" 2>/dev/null | grep db_grossgym_fitness)
    
    if [ ! -z "$DB_EXISTS" ]; then
        echo -e "${GREEN}✅ Base de datos 'db_grossgym_fitness' existe${NC}"
        
        # Contar tablas
        TABLE_COUNT=$(mysql -u jdanielmq -pJdmQ1481 -D db_grossgym_fitness -e "SHOW TABLES;" 2>/dev/null | wc -l)
        TABLE_COUNT=$((TABLE_COUNT - 1))
        
        if [ $TABLE_COUNT -gt 0 ]; then
            echo -e "${GREEN}✅ Base de datos tiene $TABLE_COUNT tablas${NC}"
        else
            echo -e "${YELLOW}⚠️  Base de datos vacía (sin tablas)${NC}"
            echo "   Las tablas se crearán automáticamente al iniciar Spring Boot"
        fi
    else
        echo -e "${YELLOW}⚠️  Base de datos 'db_grossgym_fitness' NO existe${NC}"
        echo ""
        echo "   Para crearla, ejecuta:"
        echo "   mysql -u jdanielmq -pJdmQ1481 -e \"CREATE DATABASE db_grossgym_fitness;\""
        echo ""
        echo "   O ejecuta el script automático:"
        echo "   ./configurar-proyecto.sh"
    fi
else
    echo -e "${RED}❌ Error de conexión a MySQL${NC}"
    echo ""
    echo "   Posibles causas:"
    echo "   1. Credenciales incorrectas en application.properties"
    echo "   2. MySQL no está accesible"
    echo ""
    echo "   Verifica las credenciales en:"
    echo "   src/main/resources/application.properties"
fi
echo ""

# 7. Verificar puertos
echo "7️⃣  Verificando puertos..."

# Puerto 8080 (Backend)
if lsof -i :8080 > /dev/null 2>&1; then
    echo -e "${YELLOW}⚠️  Puerto 8080 está en uso${NC}"
    echo "   Proceso usando el puerto:"
    lsof -i :8080 | grep LISTEN
else
    echo -e "${GREEN}✅ Puerto 8080 disponible (Backend)${NC}"
fi

# Puerto 4200 (Frontend)
if lsof -i :4200 > /dev/null 2>&1; then
    echo -e "${YELLOW}⚠️  Puerto 4200 está en uso${NC}"
    echo "   Proceso usando el puerto:"
    lsof -i :4200 | grep LISTEN
else
    echo -e "${GREEN}✅ Puerto 4200 disponible (Frontend)${NC}"
fi
echo ""

# 8. Verificar archivos del proyecto
echo "8️⃣  Verificando archivos del proyecto..."

if [ -f "pom.xml" ]; then
    echo -e "${GREEN}✅ pom.xml encontrado${NC}"
else
    echo -e "${RED}❌ pom.xml NO encontrado${NC}"
    echo "   Asegúrate de estar en el directorio raíz del proyecto"
fi

if [ -f "src/main/resources/application.properties" ]; then
    echo -e "${GREEN}✅ application.properties encontrado${NC}"
    echo ""
    echo "   Configuración de BD:"
    grep "spring.datasource" src/main/resources/application.properties | head -n 3
else
    echo -e "${RED}❌ application.properties NO encontrado${NC}"
fi
echo ""

# 9. Resumen
echo "======================================"
echo "📊 RESUMEN"
echo "======================================"
echo ""

# Contar problemas críticos
PROBLEMS=0

if ! command -v java &> /dev/null; then ((PROBLEMS++)); fi
if ! command -v mvn &> /dev/null; then ((PROBLEMS++)); fi

# Verificar conexión a MySQL (lo más importante)
mysql -u jdanielmq -pJdmQ1481 -e "SELECT 1;" 2>/dev/null
if [ $? -ne 0 ]; then ((PROBLEMS++)); fi

# Verificar que la BD existe
DB_EXISTS=$(mysql -u jdanielmq -pJdmQ1481 -e "SHOW DATABASES LIKE 'db_grossgym_fitness';" 2>/dev/null | grep db_grossgym_fitness)
if [ -z "$DB_EXISTS" ]; then 
    echo -e "${YELLOW}⚠️  Base de datos no existe (se puede crear fácilmente)${NC}"
fi

if [ $PROBLEMS -eq 0 ]; then
    echo -e "${GREEN}✅ VERIFICACIONES CRÍTICAS PASARON${NC}"
    echo ""
    echo "🚀 Puedes ejecutar el proyecto:"
    echo ""
    echo "   Si la BD no existe, primero ejecuta:"
    echo "   ./configurar-proyecto.sh"
    echo ""
    echo "   Luego:"
    echo "   Terminal 1 - Backend: mvn spring-boot:run"
    echo "   Terminal 2 - Frontend: cd frontend && ng serve"
else
    echo -e "${RED}❌ SE ENCONTRARON $PROBLEMS PROBLEMAS CRÍTICOS${NC}"
    echo ""
    echo "⚠️  Revisa los errores anteriores y corrígelos"
    echo ""
    echo "📖 Para ayuda, consulta:"
    echo "   - INICIO_RAPIDO.md (solución rápida)"
    echo "   - SOLUCION_PROBLEMAS.md (detallado)"
fi

echo ""
echo "======================================"

