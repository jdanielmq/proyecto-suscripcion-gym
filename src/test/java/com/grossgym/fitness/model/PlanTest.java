package com.grossgym.fitness.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests para el modelo Plan
 */
@DisplayName("Tests del modelo Plan")
class PlanTest {

    @Test
    @DisplayName("Constructor y setters deben funcionar")
    void testConstructorYSetters() {
        Plan plan = new Plan();
        plan.setIdPlan(1);
        plan.setTipoPlan("Mensual");
        plan.setMontoPlan(35000);
        plan.setDuracion(1);
        plan.setUnidad("MES");
        plan.setIsMatricula(true);
        plan.setMontoMatricula(15000);

        assertThat(plan.getIdPlan()).isEqualTo(1);
        assertThat(plan.getTipoPlan()).isEqualTo("Mensual");
        assertThat(plan.getMontoPlan()).isEqualTo(35000);
        assertThat(plan.getIsMatricula()).isTrue();
    }

    @Test
    @DisplayName("Constructor con todos los argumentos debe funcionar")
    void testConstructorCompleto() {
        Plan plan = new Plan(1, "Anual", 300000, 12, "MES", false, 0);

        assertThat(plan.getIdPlan()).isEqualTo(1);
        assertThat(plan.getTipoPlan()).isEqualTo("Anual");
        assertThat(plan.getDuracion()).isEqualTo(12);
        assertThat(plan.getIsMatricula()).isFalse();
    }

    @Test
    @DisplayName("Equals debe comparar planes correctamente")
    void testEquals() {
        Plan plan1 = new Plan(1, "Mensual", 35000, 1, "MES", true, 15000);
        Plan plan2 = new Plan(1, "Mensual", 35000, 1, "MES", true, 15000);

        assertThat(plan1).isEqualTo(plan2);
        assertThat(plan1.hashCode()).isEqualTo(plan2.hashCode());
    }
}

