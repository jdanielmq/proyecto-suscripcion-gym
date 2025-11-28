package com.grossgym.fitness.service.impl;

import com.grossgym.fitness.model.Plan;
import com.grossgym.fitness.repository.PlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios para PlanServiceImpl
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Servicio de Planes")
class PlanServiceImplTest {

    @Mock
    private PlanRepository planRepository;

    @InjectMocks
    private PlanServiceImpl planService;

    private Plan planMensual;
    private Plan planAnual;

    @BeforeEach
    void setUp() {
        planMensual = new Plan();
        planMensual.setIdPlan(1);
        planMensual.setTipoPlan("Mensual");
        planMensual.setMontoPlan(35000);
        planMensual.setMontoMatricula(15000);
        planMensual.setDuracion(1);
        planMensual.setUnidad("MESES");
        planMensual.setIsMatricula(true);

        planAnual = new Plan();
        planAnual.setIdPlan(2);
        planAnual.setTipoPlan("Anual");
        planAnual.setMontoPlan(300000);
        planAnual.setMontoMatricula(0);
        planAnual.setDuracion(12);
        planAnual.setUnidad("MESES");
        planAnual.setIsMatricula(false);
    }

    @Test
    @DisplayName("findAll() debe retornar lista de planes")
    void testFindAll_DebeRetornarListaDePlanes() {
        // Arrange
        List<Plan> planes = Arrays.asList(planMensual, planAnual);
        when(planRepository.findAll()).thenReturn(planes);

        // Act
        List<Plan> resultado = planService.findAll();

        // Assert
        assertThat(resultado)
            .isNotNull()
            .hasSize(2)
            .containsExactlyInAnyOrder(planMensual, planAnual);
        
        verify(planRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById() debe retornar plan cuando existe")
    void testFindById_DebeRetornarPlanCuandoExiste() {
        // Arrange
        Integer id = 1;
        when(planRepository.findById(id)).thenReturn(Optional.of(planMensual));

        // Act
        Optional<Plan> resultado = planService.findById(id);

        // Assert
        assertThat(resultado)
            .isPresent()
            .contains(planMensual);
        assertThat(resultado.get().getTipoPlan()).isEqualTo("Mensual");
        assertThat(resultado.get().getMontoPlan()).isEqualTo(35000);
        
        verify(planRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("findById() debe retornar Optional.empty() cuando no existe")
    void testFindById_DebeRetornarVacioCuandoNoExiste() {
        // Arrange
        Integer id = 999;
        when(planRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Plan> resultado = planService.findById(id);

        // Assert
        assertThat(resultado).isEmpty();
        
        verify(planRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("save() debe guardar y retornar el plan")
    void testSave_DebeGuardarYRetornarPlan() {
        // Arrange
        when(planRepository.save(any(Plan.class))).thenReturn(planMensual);

        // Act
        Plan resultado = planService.save(planMensual);

        // Assert
        assertThat(resultado)
            .isNotNull()
            .isEqualTo(planMensual);
        assertThat(resultado.getTipoPlan()).isEqualTo("Mensual");
        assertThat(resultado.getMontoPlan()).isEqualTo(35000);
        
        verify(planRepository, times(1)).save(planMensual);
    }

    @Test
    @DisplayName("deleteById() debe eliminar plan")
    void testDeleteById_DebeEliminarPlan() {
        // Arrange
        Integer id = 1;
        doNothing().when(planRepository).deleteById(id);

        // Act
        planService.deleteById(id);

        // Assert
        verify(planRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("save() debe actualizar plan existente")
    void testSave_DebeActualizarPlanExistente() {
        // Arrange
        planMensual.setMontoPlan(40000); // Cambiar precio
        when(planRepository.save(any(Plan.class))).thenReturn(planMensual);

        // Act
        Plan resultado = planService.save(planMensual);

        // Assert
        assertThat(resultado)
            .isNotNull()
            .isEqualTo(planMensual);
        assertThat(resultado.getMontoPlan()).isEqualTo(40000);
        
        verify(planRepository, times(1)).save(planMensual);
    }

    @Test
    @DisplayName("Plan con matrícula debe tener montoMatricula > 0")
    void testPlan_ConMatricula_DebeTenerMontoMatriculaMayorACero() {
        // Arrange & Act
        Plan plan = planMensual;

        // Assert
        assertThat(plan.getIsMatricula()).isTrue();
        assertThat(plan.getMontoMatricula()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Plan sin matrícula debe tener montoMatricula = 0")
    void testPlan_SinMatricula_DebeTenerMontoMatriculaCero() {
        // Arrange & Act
        Plan plan = planAnual;

        // Assert
        assertThat(plan.getIsMatricula()).isFalse();
        assertThat(plan.getMontoMatricula()).isEqualTo(0);
    }
}

