#!/bin/bash

# Script de Configuración Automática - Gross Gym Fitness
# Este script configura todo el proyecto automáticamente

echo "======================================"
echo "🏋️ GROSS GYM FITNESS - CONFIGURACIÓN AUTOMÁTICA"
echo "======================================"
echo ""

# Colores
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

# Variables
USUARIO_MYSQL="jdanielmq"
PASSWORD_MYSQL="JdmQ1481"
BASE_DATOS="db_grossgym_fitness"

echo -e "${BLUE}Este script configurará:${NC}"
echo "  1. Verificar conexión a MySQL"
echo "  2. Crear base de datos ($BASE_DATOS) si no existe"
echo "  3. Cargar datos iniciales (planes, tipos de pago, etc.)"
echo "  4. Compilar el proyecto (opcional)"
echo ""
echo -e "${GREEN}✅ MySQL y usuario ya están configurados${NC}"
echo ""
read -p "¿Continuar? (s/n): " -n 1 -r
echo ""

if [[ ! $REPLY =~ ^[SsYy]$ ]]; then
    echo "Configuración cancelada."
    exit 1
fi

# Paso 1: Verificar que MySQL está corriendo
echo ""
echo "1️⃣  Verificando MySQL..."
echo "======================================"

# Intentar conectar con las credenciales configuradas
mysql -u $USUARIO_MYSQL -p$PASSWORD_MYSQL -e "SELECT 1;" 2>/dev/null

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ MySQL está corriendo y accesible${NC}"
else
    echo -e "${RED}❌ No se pudo conectar a MySQL${NC}"
    echo "   Verifica que:"
    echo "   1. MySQL esté corriendo"
    echo "   2. Las credenciales sean correctas en application.properties"
    exit 1
fi

# Paso 2: Crear base de datos si no existe
echo ""
echo "2️⃣  Verificando base de datos..."
echo "======================================"

# Verificar si la base de datos existe
DB_EXISTS=$(mysql -u $USUARIO_MYSQL -p$PASSWORD_MYSQL -e "SHOW DATABASES LIKE '$BASE_DATOS';" 2>/dev/null | grep $BASE_DATOS)

if [ -z "$DB_EXISTS" ]; then
    echo "Base de datos no existe. Creándola..."
    mysql -u $USUARIO_MYSQL -p$PASSWORD_MYSQL -e "CREATE DATABASE $BASE_DATOS CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;"
    
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✅ Base de datos '$BASE_DATOS' creada${NC}"
    else
        echo -e "${RED}❌ Error al crear la base de datos${NC}"
        exit 1
    fi
else
    echo -e "${GREEN}✅ Base de datos '$BASE_DATOS' ya existe${NC}"
fi

# Paso 3: Verificar conexión a la base de datos
echo ""
echo "3️⃣  Verificando acceso a la base de datos..."
echo "======================================"

mysql -u $USUARIO_MYSQL -p$PASSWORD_MYSQL $BASE_DATOS -e "SELECT 1;" 2>/dev/null

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Acceso a base de datos '$BASE_DATOS' verificado${NC}"
else
    echo -e "${RED}❌ No se pudo acceder a la base de datos${NC}"
    exit 1
fi

# Paso 4: Cargar datos iniciales
echo ""
echo "4️⃣  Cargando datos iniciales..."
echo "======================================"

mysql -u $USUARIO_MYSQL -p$PASSWORD_MYSQL $BASE_DATOS < scripts/init.sql

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Datos iniciales cargados${NC}"
    
    # Mostrar estadísticas
    echo ""
    echo "Datos cargados:"
    mysql -u $USUARIO_MYSQL -p$PASSWORD_MYSQL $BASE_DATOS -e "SELECT 'Tipos de Pago' as Tabla, COUNT(*) as Cantidad FROM tipo_pago UNION ALL SELECT 'Planes', COUNT(*) FROM plan UNION ALL SELECT 'Estados', COUNT(*) FROM estado UNION ALL SELECT 'Instructores', COUNT(*) FROM instructor UNION ALL SELECT 'Socios', COUNT(*) FROM socio;"
else
    echo -e "${YELLOW}⚠️  Advertencia: No se pudieron cargar los datos iniciales${NC}"
    echo "   Esto no es crítico, las tablas se crearán automáticamente"
fi

# Paso 5: Verificar archivos del proyecto
echo ""
echo "5️⃣  Verificando archivos del proyecto..."
echo "======================================"

if [ -f "pom.xml" ] && [ -f "src/main/resources/application.properties" ]; then
    echo -e "${GREEN}✅ Archivos del proyecto verificados${NC}"
else
    echo -e "${RED}❌ Archivos del proyecto no encontrados${NC}"
    echo "   Asegúrate de estar en el directorio raíz del proyecto"
    exit 1
fi

# Paso 6: Compilar proyecto (opcional)
echo ""
echo "6️⃣  ¿Compilar el proyecto ahora?"
echo "======================================"
read -p "¿Compilar con Maven? (s/n): " -n 1 -r
echo ""

if [[ $REPLY =~ ^[SsYy]$ ]]; then
    echo "Compilando proyecto..."
    mvn clean compile
    
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✅ Proyecto compilado exitosamente${NC}"
    else
        echo -e "${YELLOW}⚠️  Advertencia: Error al compilar${NC}"
        echo "   Puedes compilar manualmente con: mvn clean compile"
    fi
else
    echo "Compilación omitida. Puedes compilar después con: mvn clean compile"
fi

# Resumen final
echo ""
echo "======================================"
echo "🎉 CONFIGURACIÓN COMPLETADA"
echo "======================================"
echo ""
echo -e "${GREEN}✅ Todo está listo para ejecutar el proyecto${NC}"
echo ""
echo "📋 PRÓXIMOS PASOS:"
echo ""
echo "1️⃣  Iniciar Backend (Terminal 1):"
echo -e "   ${BLUE}mvn spring-boot:run${NC}"
echo ""
echo "2️⃣  Iniciar Frontend (Terminal 2):"
echo -e "   ${BLUE}cd frontend${NC}"
echo -e "   ${BLUE}npm install${NC}  # solo primera vez"
echo -e "   ${BLUE}ng serve${NC}"
echo ""
echo "3️⃣  Abrir navegador:"
echo -e "   ${BLUE}http://localhost:4200${NC}"
echo ""
echo "📖 Para más ayuda:"
echo "   - INICIO_RAPIDO.md"
echo "   - GUIA_EJECUCION.md"
echo "   - SOLUCION_PROBLEMAS.md"
echo ""
echo "======================================"

