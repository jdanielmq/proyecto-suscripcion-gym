package com.grossgym.fitness.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests del modelo Estado")
class EstadoTest {

    @Test
    @DisplayName("Constructor y setters deben funcionar")
    void testConstructorYSetters() {
        Estado estado = new Estado();
        estado.setIdEstado(1);
        estado.setDescripcion("Activo");
        estado.setHabilitado(true);

        assertThat(estado.getIdEstado()).isEqualTo(1);
        assertThat(estado.getDescripcion()).isEqualTo("Activo");
        assertThat(estado.getHabilitado()).isTrue();
    }

    @Test
    @DisplayName("Constructor completo debe funcionar")
    void testConstructorCompleto() {
        Estado estado = new Estado(1, "Vencido", false);

        assertThat(estado.getIdEstado()).isEqualTo(1);
        assertThat(estado.getDescripcion()).isEqualTo("Vencido");
        assertThat(estado.getHabilitado()).isFalse();
    }
}

