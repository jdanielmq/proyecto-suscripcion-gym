package com.grossgym.fitness.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests del modelo TipoPago")
class TipoPagoTest {

    @Test
    @DisplayName("Constructor y setters deben funcionar")
    void testConstructorYSetters() {
        TipoPago tipoPago = new TipoPago();
        tipoPago.setIdPago(1);
        tipoPago.setDescripcion("Efectivo");
        tipoPago.setEstado(true);

        assertThat(tipoPago.getIdPago()).isEqualTo(1);
        assertThat(tipoPago.getDescripcion()).isEqualTo("Efectivo");
        assertThat(tipoPago.getEstado()).isTrue();
    }

    @Test
    @DisplayName("Constructor completo debe funcionar")
    void testConstructorCompleto() {
        TipoPago tipoPago = new TipoPago(1, "Tarjeta", true);

        assertThat(tipoPago.getIdPago()).isEqualTo(1);
        assertThat(tipoPago.getDescripcion()).isEqualTo("Tarjeta");
    }
}

