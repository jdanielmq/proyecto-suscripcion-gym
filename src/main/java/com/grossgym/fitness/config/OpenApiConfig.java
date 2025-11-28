package com.grossgym.fitness.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de OpenAPI (Swagger) para documentación de la API
 * Acceder a la documentación en: http://localhost:8080/api/swagger-ui.html
 */
@Configuration
public class OpenApiConfig {

    @Value("${server.servlet.context-path:/}")
    private String contextPath;

    @Bean
    public OpenAPI grossGymOpenAPI() {
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080" + contextPath);
        localServer.setDescription("Servidor Local de Desarrollo");

        Contact contact = new Contact();
        contact.setEmail("info@grossgym.com");
        contact.setName("Gross Gym Fitness Team");
        contact.setUrl("https://www.grossgym.com");

        License license = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Gross Gym Fitness - API de Gestión de Suscripciones")
                .version("1.0.0")
                .contact(contact)
                .description("""
                        API REST para la gestión completa de un gimnasio:
                        
                        - Gestión de Socios (CRUD completo)
                        - Gestión de Suscripciones (CRUD completo)
                        - Gestión de Planes de membresía
                        - Gestión de Tipos de Pago
                        - Gestión de Estados de suscripción
                        - Evaluaciones InBody de composición corporal
                        
                        Características:
                        - Cálculo automático de fechas de término
                        - Validaciones de negocio
                        - Relaciones entre entidades
                        - Búsquedas y filtros avanzados
                        
                        Tecnologías:
                        - Spring Boot 3.2.0
                        - Java 21
                        - Spring Data JPA
                        - MySQL 8.0
                        """)
                .termsOfService("https://www.grossgym.com/terms")
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}

