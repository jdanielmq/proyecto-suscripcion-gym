package com.grossgym.fitness.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests para el modelo Suscripcion
 */
@DisplayName("Tests del modelo Suscripcion")
class SuscripcionTest {

    @Test
    @DisplayName("Constructor y setters deben funcionar")
    void testConstructorYSetters() {
        Suscripcion suscripcion = new Suscripcion();
        LocalDateTime now = LocalDateTime.now();
        
        suscripcion.setIdSuscripcion(1);
        suscripcion.setNroTransaccion("TRX-001");
        suscripcion.setFechaCreacion(now);
        suscripcion.setMontoPlan(35000);
        suscripcion.setMontoMatricula(15000);
        suscripcion.setNroCuotas(1);

        assertThat(suscripcion.getIdSuscripcion()).isEqualTo(1);
        assertThat(suscripcion.getNroTransaccion()).isEqualTo("TRX-001");
        assertThat(suscripcion.getMontoPlan()).isEqualTo(35000);
        assertThat(suscripcion.getFechaCreacion()).isEqualTo(now);
    }

    @Test
    @DisplayName("Suscripción debe poder asociarse con entidades")
    void testAsociacionConEntidades() {
        Suscripcion suscripcion = new Suscripcion();
        
        Socio socio = new Socio();
        socio.setRut("12345678-9");
        
        Plan plan = new Plan();
        plan.setIdPlan(1);
        
        TipoPago tipoPago = new TipoPago();
        tipoPago.setIdPago(1);
        
        Estado estado = new Estado();
        estado.setIdEstado(1);
        
        suscripcion.setSocio(socio);
        suscripcion.setPlan(plan);
        suscripcion.setTipoPago(tipoPago);
        suscripcion.setEstado(estado);

        assertThat(suscripcion.getSocio()).isNotNull();
        assertThat(suscripcion.getPlan()).isNotNull();
        assertThat(suscripcion.getTipoPago()).isNotNull();
        assertThat(suscripcion.getEstado()).isNotNull();
    }
}

