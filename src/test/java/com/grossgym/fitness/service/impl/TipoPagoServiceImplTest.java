package com.grossgym.fitness.service.impl;

import com.grossgym.fitness.model.TipoPago;
import com.grossgym.fitness.repository.TipoPagoRepository;
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
 * Tests unitarios para TipoPagoServiceImpl
 * Usa Mockito para simular el comportamiento del repositorio
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Servicio de Tipos de Pago")
class TipoPagoServiceImplTest {

    @Mock
    private TipoPagoRepository tipoPagoRepository;

    @InjectMocks
    private TipoPagoServiceImpl tipoPagoService;

    private TipoPago tipoPagoEfectivo;
    private TipoPago tipoPagoTarjeta;

    @BeforeEach
    void setUp() {
        // Preparar datos de prueba
        tipoPagoEfectivo = new TipoPago();
        tipoPagoEfectivo.setIdPago(1);
        tipoPagoEfectivo.setDescripcion("Efectivo");
        tipoPagoEfectivo.setEstado(true);

        tipoPagoTarjeta = new TipoPago();
        tipoPagoTarjeta.setIdPago(2);
        tipoPagoTarjeta.setDescripcion("Tarjeta de Crédito");
        tipoPagoTarjeta.setEstado(true);
    }

    // ========================================
    // TEST: findAll()
    // ========================================

    @Test
    @DisplayName("findAll() debe retornar lista de tipos de pago")
    void testFindAll_DebeRetornarListaDeTiposPago() {
        // Arrange
        List<TipoPago> tiposPago = Arrays.asList(tipoPagoEfectivo, tipoPagoTarjeta);
        when(tipoPagoRepository.findAll()).thenReturn(tiposPago);

        // Act
        List<TipoPago> resultado = tipoPagoService.findAll();

        // Assert
        assertThat(resultado)
            .isNotNull()
            .hasSize(2)
            .containsExactly(tipoPagoEfectivo, tipoPagoTarjeta);
        
        verify(tipoPagoRepository, times(1)).findAll();
    }

    // ========================================
    // TEST: findById()
    // ========================================

    @Test
    @DisplayName("findById() debe retornar tipo de pago cuando existe")
    void testFindById_DebeRetornarTipoPagoCuandoExiste() {
        // Arrange
        when(tipoPagoRepository.findById(1)).thenReturn(Optional.of(tipoPagoEfectivo));

        // Act
        Optional<TipoPago> resultado = tipoPagoService.findById(1);

        // Assert
        assertThat(resultado)
            .isPresent()
            .contains(tipoPagoEfectivo);
        assertThat(resultado.get().getDescripcion()).isEqualTo("Efectivo");
        
        verify(tipoPagoRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("findById() debe retornar vacío cuando no existe")
    void testFindById_DebeRetornarVacioCuandoNoExiste() {
        // Arrange
        when(tipoPagoRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<TipoPago> resultado = tipoPagoService.findById(999);

        // Assert
        assertThat(resultado).isEmpty();
        
        verify(tipoPagoRepository, times(1)).findById(999);
    }

    // ========================================
    // TEST: save()
    // ========================================

    @Test
    @DisplayName("save() debe guardar y retornar el tipo de pago")
    void testSave_DebeGuardarYRetornarTipoPago() {
        // Arrange
        when(tipoPagoRepository.save(any(TipoPago.class))).thenReturn(tipoPagoEfectivo);

        // Act
        TipoPago resultado = tipoPagoService.save(tipoPagoEfectivo);

        // Assert
        assertThat(resultado)
            .isNotNull()
            .isEqualTo(tipoPagoEfectivo);
        assertThat(resultado.getDescripcion()).isEqualTo("Efectivo");
        
        verify(tipoPagoRepository, times(1)).save(tipoPagoEfectivo);
    }

    @Test
    @DisplayName("save() debe actualizar tipo de pago existente")
    void testSave_DebeActualizarTipoPagoExistente() {
        // Arrange
        tipoPagoEfectivo.setDescripcion("Efectivo - Actualizado");
        when(tipoPagoRepository.save(any(TipoPago.class))).thenReturn(tipoPagoEfectivo);

        // Act
        TipoPago resultado = tipoPagoService.save(tipoPagoEfectivo);

        // Assert
        assertThat(resultado)
            .isNotNull()
            .isEqualTo(tipoPagoEfectivo);
        assertThat(resultado.getDescripcion()).isEqualTo("Efectivo - Actualizado");
        
        verify(tipoPagoRepository, times(1)).save(tipoPagoEfectivo);
    }

    // ========================================
    // TEST: deleteById()
    // ========================================

    @Test
    @DisplayName("deleteById() debe eliminar tipo de pago")
    void testDeleteById_DebeEliminarTipoPago() {
        // Arrange
        doNothing().when(tipoPagoRepository).deleteById(1);

        // Act
        tipoPagoService.deleteById(1);

        // Assert
        verify(tipoPagoRepository, times(1)).deleteById(1);
    }

    // ========================================
    // TEST: findByEstado()
    // ========================================

    @Test
    @DisplayName("findByEstado() debe retornar tipos de pago activos")
    void testFindByEstado_DebeRetornarTiposPagoActivos() {
        // Arrange
        List<TipoPago> tiposPagoActivos = Arrays.asList(tipoPagoEfectivo, tipoPagoTarjeta);
        when(tipoPagoRepository.findByEstado(true)).thenReturn(tiposPagoActivos);

        // Act
        List<TipoPago> resultado = tipoPagoService.findByEstado(true);

        // Assert
        assertThat(resultado)
            .isNotNull()
            .hasSize(2)
            .allMatch(tp -> tp.getEstado());
        
        verify(tipoPagoRepository, times(1)).findByEstado(true);
    }

    @Test
    @DisplayName("findByEstado() debe retornar tipos de pago inactivos")
    void testFindByEstado_DebeRetornarTiposPagoInactivos() {
        // Arrange
        TipoPago tipoPagoInactivo = new TipoPago();
        tipoPagoInactivo.setIdPago(3);
        tipoPagoInactivo.setDescripcion("Transferencia");
        tipoPagoInactivo.setEstado(false);
        
        when(tipoPagoRepository.findByEstado(false)).thenReturn(List.of(tipoPagoInactivo));

        // Act
        List<TipoPago> resultado = tipoPagoService.findByEstado(false);

        // Assert
        assertThat(resultado)
            .isNotNull()
            .hasSize(1)
            .allMatch(tp -> !tp.getEstado());
        
        verify(tipoPagoRepository, times(1)).findByEstado(false);
    }

    // ========================================
    // TEST: Escenarios de validación
    // ========================================

    @Test
    @DisplayName("findAll() debe retornar lista vacía cuando no hay tipos de pago")
    void testFindAll_DebeRetornarListaVaciaCuandoNoHayDatos() {
        // Arrange
        when(tipoPagoRepository.findAll()).thenReturn(List.of());

        // Act
        List<TipoPago> resultado = tipoPagoService.findAll();

        // Assert
        assertThat(resultado)
            .isNotNull()
            .isEmpty();
        
        verify(tipoPagoRepository, times(1)).findAll();
    }
}

