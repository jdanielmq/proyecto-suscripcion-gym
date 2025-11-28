package com.grossgym.fitness.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests para el modelo Socio
 */
@DisplayName("Tests del modelo Socio")
class SocioTest {

    @Test
    @DisplayName("Constructor sin argumentos debe crear socio vacío")
    void testConstructorSinArgumentos() {
        Socio socio = new Socio();
        assertThat(socio).isNotNull();
    }

    @Test
    @DisplayName("Constructor con argumentos debe crear socio con datos")
    void testConstructorConArgumentos() {
        Socio socio = new Socio(
            "12345678-9",
            "Juan",
            "Pérez",
            "González",
            "Masculino",
            "juan@example.com",
            "+56912345678",
            true
        );

        assertThat(socio.getRut()).isEqualTo("12345678-9");
        assertThat(socio.getNombres()).isEqualTo("Juan");
        assertThat(socio.getApellidoPaterno()).isEqualTo("Pérez");
    }

    @Test
    @DisplayName("Setters y getters deben funcionar correctamente")
    void testSettersYGetters() {
        Socio socio = new Socio();
        
        socio.setRut("98765432-1");
        socio.setNombres("María");
        socio.setApellidoPaterno("Silva");
        socio.setApellidoMaterno("Torres");
        socio.setGenero("Femenino");
        socio.setCorreo("maria@example.com");
        socio.setCelular("+56987654321");
        socio.setHabilitado(true);

        assertThat(socio.getRut()).isEqualTo("98765432-1");
        assertThat(socio.getNombres()).isEqualTo("María");
        assertThat(socio.getCorreo()).isEqualTo("maria@example.com");
        assertThat(socio.getHabilitado()).isTrue();
    }

    @Test
    @DisplayName("Equals debe comparar socios correctamente")
    void testEquals() {
        Socio socio1 = new Socio("12345678-9", "Juan", "Pérez", "González", 
                                 "Masculino", "juan@example.com", "+56912345678", true);
        Socio socio2 = new Socio("12345678-9", "Juan", "Pérez", "González",
                                 "Masculino", "juan@example.com", "+56912345678", true);

        assertThat(socio1).isEqualTo(socio2);
        assertThat(socio1.hashCode()).isEqualTo(socio2.hashCode());
    }

    @Test
    @DisplayName("ToString debe retornar representación string")
    void testToString() {
        Socio socio = new Socio("12345678-9", "Juan", "Pérez", "González",
                                "Masculino", "juan@example.com", "+56912345678", true);

        String resultado = socio.toString();
        
        assertThat(resultado).contains("12345678-9");
        assertThat(resultado).contains("Juan");
    }
}

