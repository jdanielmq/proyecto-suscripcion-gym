# Multi-stage build para Spring Boot 3 con Java 17
# Backend: Gross Gym Fitness - Sistema de Gestión de Suscripciones

# ========================================
# Stage 1: Build (Compilación)
# ========================================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY src ./src

# Compilar el proyecto (skip tests para build más rápido)
RUN mvn clean package -DskipTests

# ========================================
# Stage 2: Runtime (Ejecución)
# ========================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Instalar wget para health check
RUN apk add --no-cache wget

# Crear usuario no-root para seguridad
RUN addgroup -S spring && adduser -S spring -G spring

# Copiar el JAR compilado desde el stage de build
COPY --from=build /app/target/*.jar app.jar

# Cambiar permisos y propietario
RUN chown spring:spring app.jar

# Cambiar a usuario no-root
USER spring:spring

# Exponer el puerto de la aplicación
EXPOSE 8080

# Variables de entorno por defecto (pueden sobrescribirse)
ENV SPRING_PROFILES_ACTIVE=dev \
    JAVA_OPTS="-Xms256m -Xmx512m" \
    DB_HOST=host.docker.internal \
    DB_PORT=3306 \
    DB_NAME=db_grossgym_fitness \
    DB_USER=jdanielmq \
    DB_PASS=JdmQ1481

# Health check para verificar que la aplicación está corriendo
HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/api/planes || exit 1

# Comando de inicio
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.datasource.url=jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME} -Dspring.datasource.username=${DB_USER} -Dspring.datasource.password=${DB_PASS} -jar app.jar"]

