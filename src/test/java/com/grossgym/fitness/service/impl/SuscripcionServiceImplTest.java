package com.grossgym.fitness.service.impl;

import com.grossgym.fitness.model.*;
import com.grossgym.fitness.repository.SuscripcionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios para SuscripcionServiceImpl
 * Usa Mockito para simular el comportamiento del repositorio
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Servicio de Suscripciones")
class SuscripcionServiceImplTest {

    @Mock
    private SuscripcionRepository suscripcionRepository;

    @InjectMocks
    private SuscripcionServiceImpl suscripcionService;

    private Suscripcion suscripcion1;
    private Suscripcion suscripcion2;
    private Socio socio;
    private Plan planMensual;
    private Plan planAnual;
    private TipoPago tipoPago;
    private Estado estado;

    @BeforeEach
    void setUp() {
        // Preparar Socio
        socio = new Socio();
        socio.setRut("12345678-9");
        socio.setNombres("Juan Pablo");
        socio.setApellidoPaterno("Soto");
        socio.setApellidoMaterno("González");
        socio.setGenero("Masculino");
        socio.setCorreo("juan@example.com");
        socio.setCelular("+56912345678");
        socio.setHabilitado(true);

        // Preparar Plan Mensual
        planMensual = new Plan();
        planMensual.setIdPlan(1);
        planMensual.setTipoPlan("Mensual");
        planMensual.setMontoPlan(35000);
        planMensual.setDuracion(1);
        planMensual.setUnidad("MES");
        planMensual.setIsMatricula(true);
        planMensual.setMontoMatricula(15000);

        // Preparar Plan Anual
        planAnual = new Plan();
        planAnual.setIdPlan(2);
        planAnual.setTipoPlan("Anual");
        planAnual.setMontoPlan(300000);
        planAnual.setDuracion(12);
        planAnual.setUnidad("MES");
        planAnual.setIsMatricula(false);
        planAnual.setMontoMatricula(0);

        // Preparar Tipo de Pago
        tipoPago = new TipoPago();
        tipoPago.setIdPago(1);
        tipoPago.setDescripcion("Efectivo");
        tipoPago.setEstado(true);

        // Preparar Estado
        estado = new Estado();
        estado.setIdEstado(1);
        estado.setDescripcion("Activo");
        estado.setHabilitado(true);

        // Preparar Suscripción 1
        suscripcion1 = new Suscripcion();
        suscripcion1.setIdSuscripcion(1);
        suscripcion1.setFechaCreacion(LocalDateTime.now());
        suscripcion1.setNroTransaccion("TRX-001");
        suscripcion1.setSocio(socio);
        suscripcion1.setPlan(planMensual);
        suscripcion1.setTipoPago(tipoPago);
        suscripcion1.setMontoPlan(35000);
        suscripcion1.setMontoMatricula(15000);
        suscripcion1.setNroCuotas(1);
        suscripcion1.setFechaTermino(LocalDateTime.now().plusMonths(1));
        suscripcion1.setEstado(estado);

        // Preparar Suscripción 2
        suscripcion2 = new Suscripcion();
        suscripcion2.setIdSuscripcion(2);
        suscripcion2.setFechaCreacion(LocalDateTime.now());
        suscripcion2.setNroTransaccion("TRX-002");
        suscripcion2.setSocio(socio);
        suscripcion2.setPlan(planAnual);
        suscripcion2.setTipoPago(tipoPago);
        suscripcion2.setMontoPlan(300000);
        suscripcion2.setMontoMatricula(0);
        suscripcion2.setNroCuotas(12);
        suscripcion2.setFechaTermino(LocalDateTime.now().plusYears(1));
        suscripcion2.setEstado(estado);
    }

    // ========================================
    // TEST: findAll()
    // ========================================

    @Test
    @DisplayName("findAll() debe retornar lista de suscripciones")
    void testFindAll_DebeRetornarListaDeSuscripciones() {
        // Arrange
        List<Suscripcion> suscripciones = Arrays.asList(suscripcion1, suscripcion2);
        when(suscripcionRepository.findAll()).thenReturn(suscripciones);

        // Act
        List<Suscripcion> resultado = suscripcionService.findAll();

        // Assert
        assertThat(resultado)
            .isNotNull()
            .hasSize(2)
            .containsExactly(suscripcion1, suscripcion2);
        
        verify(suscripcionRepository, times(1)).findAll();
    }

    // ========================================
    // TEST: findById()
    // ========================================

    @Test
    @DisplayName("findById() debe retornar suscripción cuando existe")
    void testFindById_DebeRetornarSuscripcionCuandoExiste() {
        // Arrange
        when(suscripcionRepository.findById(1)).thenReturn(Optional.of(suscripcion1));

        // Act
        Optional<Suscripcion> resultado = suscripcionService.findById(1);

        // Assert
        assertThat(resultado)
            .isPresent()
            .contains(suscripcion1);
        assertThat(resultado.get().getNroTransaccion()).isEqualTo("TRX-001");
        
        verify(suscripcionRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("findById() debe retornar vacío cuando no existe")
    void testFindById_DebeRetornarVacioCuandoNoExiste() {
        // Arrange
        when(suscripcionRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<Suscripcion> resultado = suscripcionService.findById(999);

        // Assert
        assertThat(resultado).isEmpty();
        
        verify(suscripcionRepository, times(1)).findById(999);
    }

    // ========================================
    // TEST: save() - Nueva suscripción
    // ========================================

    @Test
    @DisplayName("save() debe guardar nueva suscripción y calcular fecha de término")
    void testSave_DebeGuardarNuevaSuscripcionYCalcularFechaTermino() {
        // Arrange
        Suscripcion nuevaSuscripcion = new Suscripcion();
        nuevaSuscripcion.setSocio(socio);
        nuevaSuscripcion.setPlan(planMensual);
        nuevaSuscripcion.setTipoPago(tipoPago);
        nuevaSuscripcion.setMontoPlan(35000);
        nuevaSuscripcion.setMontoMatricula(15000);
        nuevaSuscripcion.setNroTransaccion("TRX-003");
        nuevaSuscripcion.setEstado(estado);

        when(suscripcionRepository.save(any(Suscripcion.class))).thenAnswer(invocation -> {
            Suscripcion sus = invocation.getArgument(0);
            sus.setIdSuscripcion(3);
            return sus;
        });

        // Act
        Suscripcion resultado = suscripcionService.save(nuevaSuscripcion);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdSuscripcion()).isEqualTo(3);
        assertThat(resultado.getFechaCreacion()).isNotNull();
        assertThat(resultado.getFechaTermino()).isNotNull();
        assertThat(resultado.getFechaTermino()).isAfter(resultado.getFechaCreacion());
        
        verify(suscripcionRepository, times(1)).save(any(Suscripcion.class));
    }

    @Test
    @DisplayName("save() debe lanzar excepción cuando socio es null")
    void testSave_DebeLanzarExcepcionCuandoSocioEsNull() {
        // Arrange
        Suscripcion suscripcionSinSocio = new Suscripcion();
        suscripcionSinSocio.setPlan(planMensual);
        suscripcionSinSocio.setTipoPago(tipoPago);

        // Act & Assert
        assertThatThrownBy(() -> suscripcionService.save(suscripcionSinSocio))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("La suscripción debe tener un socio asociado");
        
        verify(suscripcionRepository, never()).save(any(Suscripcion.class));
    }

    @Test
    @DisplayName("save() debe lanzar excepción cuando plan es null")
    void testSave_DebeLanzarExcepcionCuandoPlanEsNull() {
        // Arrange
        Suscripcion suscripcionSinPlan = new Suscripcion();
        suscripcionSinPlan.setSocio(socio);
        suscripcionSinPlan.setTipoPago(tipoPago);

        // Act & Assert
        assertThatThrownBy(() -> suscripcionService.save(suscripcionSinPlan))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("La suscripción debe tener un plan asociado");
        
        verify(suscripcionRepository, never()).save(any(Suscripcion.class));
    }

    @Test
    @DisplayName("save() debe lanzar excepción cuando tipo de pago es null")
    void testSave_DebeLanzarExcepcionCuandoTipoPagoEsNull() {
        // Arrange
        Suscripcion suscripcionSinTipoPago = new Suscripcion();
        suscripcionSinTipoPago.setSocio(socio);
        suscripcionSinTipoPago.setPlan(planMensual);

        // Act & Assert
        assertThatThrownBy(() -> suscripcionService.save(suscripcionSinTipoPago))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("La suscripción debe tener un tipo de pago");
        
        verify(suscripcionRepository, never()).save(any(Suscripcion.class));
    }

    // ========================================
    // TEST: save() - Actualizar suscripción
    // ========================================

    @Test
    @DisplayName("save() debe actualizar suscripción existente")
    void testSave_DebeActualizarSuscripcionExistente() {
        // Arrange
        suscripcion1.setMontoPlan(40000);
        when(suscripcionRepository.save(any(Suscripcion.class))).thenReturn(suscripcion1);

        // Act
        Suscripcion resultado = suscripcionService.save(suscripcion1);

        // Assert
        assertThat(resultado)
            .isNotNull()
            .isEqualTo(suscripcion1);
        assertThat(resultado.getMontoPlan()).isEqualTo(40000);
        
        verify(suscripcionRepository, times(1)).save(suscripcion1);
    }

    // ========================================
    // TEST: deleteById()
    // ========================================

    @Test
    @DisplayName("deleteById() debe eliminar suscripción")
    void testDeleteById_DebeEliminarSuscripcion() {
        // Arrange
        doNothing().when(suscripcionRepository).deleteById(1);

        // Act
        suscripcionService.deleteById(1);

        // Assert
        verify(suscripcionRepository, times(1)).deleteById(1);
    }

    // ========================================
    // TEST: findBySocio()
    // ========================================

    @Test
    @DisplayName("findBySocio() debe retornar suscripciones del socio")
    void testFindBySocio_DebeRetornarSuscripcionesDelSocio() {
        // Arrange
        List<Suscripcion> suscripciones = Arrays.asList(suscripcion1, suscripcion2);
        when(suscripcionRepository.findBySocio(socio)).thenReturn(suscripciones);

        // Act
        List<Suscripcion> resultado = suscripcionService.findBySocio(socio);

        // Assert
        assertThat(resultado)
            .isNotNull()
            .hasSize(2)
            .allMatch(sus -> sus.getSocio().equals(socio));
        
        verify(suscripcionRepository, times(1)).findBySocio(socio);
    }

    // ========================================
    // TEST: findByEstado()
    // ========================================

    @Test
    @DisplayName("findByEstado() debe retornar suscripciones activas")
    void testFindByEstado_DebeRetornarSuscripcionesActivas() {
        // Arrange
        List<Suscripcion> suscripcionesActivas = Arrays.asList(suscripcion1, suscripcion2);
        when(suscripcionRepository.findByEstado(estado)).thenReturn(suscripcionesActivas);

        // Act
        List<Suscripcion> resultado = suscripcionService.findByEstado(estado);

        // Assert
        assertThat(resultado)
            .isNotNull()
            .hasSize(2)
            .allMatch(sus -> sus.getEstado().equals(estado));
        
        verify(suscripcionRepository, times(1)).findByEstado(estado);
    }

    // ========================================
    // TEST: findByNroTransaccion()
    // ========================================

    @Test
    @DisplayName("findByNroTransaccion() debe retornar suscripción por número de transacción")
    void testFindByNroTransaccion_DebeRetornarSuscripcion() {
        // Arrange
        when(suscripcionRepository.findByNroTransaccion("TRX-001")).thenReturn(suscripcion1);

        // Act
        Suscripcion resultado = suscripcionService.findByNroTransaccion("TRX-001");

        // Assert
        assertThat(resultado)
            .isNotNull()
            .isEqualTo(suscripcion1);
        assertThat(resultado.getNroTransaccion()).isEqualTo("TRX-001");
        
        verify(suscripcionRepository, times(1)).findByNroTransaccion("TRX-001");
    }

    @Test
    @DisplayName("findByNroTransaccion() debe retornar null cuando no existe")
    void testFindByNroTransaccion_DebeRetornarNullCuandoNoExiste() {
        // Arrange
        when(suscripcionRepository.findByNroTransaccion("TRX-999")).thenReturn(null);

        // Act
        Suscripcion resultado = suscripcionService.findByNroTransaccion("TRX-999");

        // Assert
        assertThat(resultado).isNull();
        
        verify(suscripcionRepository, times(1)).findByNroTransaccion("TRX-999");
    }

    // ========================================
    // TEST: findSuscripcionesVigentes()
    // ========================================

    @Test
    @DisplayName("findSuscripcionesVigentes() debe retornar suscripciones vigentes")
    void testFindSuscripcionesVigentes_DebeRetornarSuscripcionesVigentes() {
        // Arrange
        List<Suscripcion> suscripcionesVigentes = Arrays.asList(suscripcion1, suscripcion2);
        when(suscripcionRepository.findSuscripcionesVigentes(any(LocalDateTime.class)))
            .thenReturn(suscripcionesVigentes);

        // Act
        List<Suscripcion> resultado = suscripcionService.findSuscripcionesVigentes();

        // Assert
        assertThat(resultado)
            .isNotNull()
            .hasSize(2);
        
        verify(suscripcionRepository, times(1))
            .findSuscripcionesVigentes(any(LocalDateTime.class));
    }

    // ========================================
    // TEST: Cálculo de fecha de término
    // ========================================

    @Test
    @DisplayName("save() debe calcular correctamente fecha de término para plan mensual")
    void testSave_DebeCalcularFechaTerminoParaPlanMensual() {
        // Arrange
        Suscripcion nuevaSuscripcion = new Suscripcion();
        nuevaSuscripcion.setSocio(socio);
        nuevaSuscripcion.setPlan(planMensual);
        nuevaSuscripcion.setTipoPago(tipoPago);
        nuevaSuscripcion.setMontoPlan(35000);
        nuevaSuscripcion.setMontoMatricula(15000);
        nuevaSuscripcion.setNroTransaccion("TRX-004");
        nuevaSuscripcion.setEstado(estado);

        when(suscripcionRepository.save(any(Suscripcion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Suscripcion resultado = suscripcionService.save(nuevaSuscripcion);

        // Assert
        assertThat(resultado.getFechaTermino())
            .isNotNull()
            .isAfter(resultado.getFechaCreacion())
            .isBeforeOrEqualTo(resultado.getFechaCreacion().plusMonths(1).plusDays(1));
    }

    @Test
    @DisplayName("save() debe calcular correctamente fecha de término para plan anual")
    void testSave_DebeCalcularFechaTerminoParaPlanAnual() {
        // Arrange
        Suscripcion nuevaSuscripcion = new Suscripcion();
        nuevaSuscripcion.setSocio(socio);
        nuevaSuscripcion.setPlan(planAnual);
        nuevaSuscripcion.setTipoPago(tipoPago);
        nuevaSuscripcion.setMontoPlan(300000);
        nuevaSuscripcion.setMontoMatricula(0);
        nuevaSuscripcion.setNroTransaccion("TRX-005");
        nuevaSuscripcion.setEstado(estado);

        when(suscripcionRepository.save(any(Suscripcion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Suscripcion resultado = suscripcionService.save(nuevaSuscripcion);

        // Assert
        assertThat(resultado.getFechaTermino())
            .isNotNull()
            .isAfter(resultado.getFechaCreacion())
            .isAfter(resultado.getFechaCreacion().plusMonths(11));
    }

    // ========================================
    // TEST: Escenarios de validación
    // ========================================

    @Test
    @DisplayName("findAll() debe retornar lista vacía cuando no hay suscripciones")
    void testFindAll_DebeRetornarListaVaciaCuandoNoHayDatos() {
        // Arrange
        when(suscripcionRepository.findAll()).thenReturn(List.of());

        // Act
        List<Suscripcion> resultado = suscripcionService.findAll();

        // Assert
        assertThat(resultado)
            .isNotNull()
            .isEmpty();
        
        verify(suscripcionRepository, times(1)).findAll();
    }
}

