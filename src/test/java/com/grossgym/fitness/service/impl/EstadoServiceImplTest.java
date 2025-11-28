package com.grossgym.fitness.service.impl;

import com.grossgym.fitness.model.Estado;
import com.grossgym.fitness.repository.EstadoRepository;
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
 * Tests unitarios para EstadoServiceImpl
 * Usa Mockito para simular el comportamiento del repositorio
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Servicio de Estados")
class EstadoServiceImplTest {

    @Mock
    private EstadoRepository estadoRepository;

    @InjectMocks
    private EstadoServiceImpl estadoService;

    private Estado estadoActivo;
    private Estado estadoVencido;

    @BeforeEach
    void setUp() {
        // Preparar datos de prueba
        estadoActivo = new Estado();
        estadoActivo.setIdEstado(1);
        estadoActivo.setDescripcion("Activo");
        estadoActivo.setHabilitado(true);

        estadoVencido = new Estado();
        estadoVencido.setIdEstado(2);
        estadoVencido.setDescripcion("Vencido");
        estadoVencido.setHabilitado(true);
    }

    // ========================================
    // TEST: findAll()
    // ========================================

    @Test
    @DisplayName("findAll() debe retornar lista de estados")
    void testFindAll_DebeRetornarListaDeEstados() {
        // Arrange
        List<Estado> estados = Arrays.asList(estadoActivo, estadoVencido);
        when(estadoRepository.findAll()).thenReturn(estados);

        // Act
        List<Estado> resultado = estadoService.findAll();

        // Assert
        assertThat(resultado)
            .isNotNull()
            .hasSize(2)
            .containsExactly(estadoActivo, estadoVencido);
        
        verify(estadoRepository, times(1)).findAll();
    }

    // ========================================
    // TEST: findById()
    // ========================================

    @Test
    @DisplayName("findById() debe retornar estado cuando existe")
    void testFindById_DebeRetornarEstadoCuandoExiste() {
        // Arrange
        when(estadoRepository.findById(1)).thenReturn(Optional.of(estadoActivo));

        // Act
        Optional<Estado> resultado = estadoService.findById(1);

        // Assert
        assertThat(resultado)
            .isPresent()
            .contains(estadoActivo);
        assertThat(resultado.get().getDescripcion()).isEqualTo("Activo");
        
        verify(estadoRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("findById() debe retornar vacío cuando no existe")
    void testFindById_DebeRetornarVacioCuandoNoExiste() {
        // Arrange
        when(estadoRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<Estado> resultado = estadoService.findById(999);

        // Assert
        assertThat(resultado).isEmpty();
        
        verify(estadoRepository, times(1)).findById(999);
    }

    // ========================================
    // TEST: save()
    // ========================================

    @Test
    @DisplayName("save() debe guardar y retornar el estado")
    void testSave_DebeGuardarYRetornarEstado() {
        // Arrange
        when(estadoRepository.save(any(Estado.class))).thenReturn(estadoActivo);

        // Act
        Estado resultado = estadoService.save(estadoActivo);

        // Assert
        assertThat(resultado)
            .isNotNull()
            .isEqualTo(estadoActivo);
        assertThat(resultado.getDescripcion()).isEqualTo("Activo");
        
        verify(estadoRepository, times(1)).save(estadoActivo);
    }

    @Test
    @DisplayName("save() debe actualizar estado existente")
    void testSave_DebeActualizarEstadoExistente() {
        // Arrange
        estadoActivo.setDescripcion("Activo - Actualizado");
        when(estadoRepository.save(any(Estado.class))).thenReturn(estadoActivo);

        // Act
        Estado resultado = estadoService.save(estadoActivo);

        // Assert
        assertThat(resultado)
            .isNotNull()
            .isEqualTo(estadoActivo);
        assertThat(resultado.getDescripcion()).isEqualTo("Activo - Actualizado");
        
        verify(estadoRepository, times(1)).save(estadoActivo);
    }

    // ========================================
    // TEST: deleteById()
    // ========================================

    @Test
    @DisplayName("deleteById() debe eliminar estado")
    void testDeleteById_DebeEliminarEstado() {
        // Arrange
        doNothing().when(estadoRepository).deleteById(1);

        // Act
        estadoService.deleteById(1);

        // Assert
        verify(estadoRepository, times(1)).deleteById(1);
    }

    // ========================================
    // TEST: findByHabilitado()
    // ========================================

    @Test
    @DisplayName("findByHabilitado() debe retornar estados habilitados")
    void testFindByHabilitado_DebeRetornarEstadosHabilitados() {
        // Arrange
        List<Estado> estadosHabilitados = Arrays.asList(estadoActivo, estadoVencido);
        when(estadoRepository.findByHabilitado(true)).thenReturn(estadosHabilitados);

        // Act
        List<Estado> resultado = estadoService.findByHabilitado(true);

        // Assert
        assertThat(resultado)
            .isNotNull()
            .hasSize(2)
            .allMatch(estado -> estado.getHabilitado());
        
        verify(estadoRepository, times(1)).findByHabilitado(true);
    }

    @Test
    @DisplayName("findByHabilitado() debe retornar estados deshabilitados")
    void testFindByHabilitado_DebeRetornarEstadosDeshabilitados() {
        // Arrange
        Estado estadoDeshabilitado = new Estado();
        estadoDeshabilitado.setIdEstado(3);
        estadoDeshabilitado.setDescripcion("Suspendido");
        estadoDeshabilitado.setHabilitado(false);
        
        when(estadoRepository.findByHabilitado(false)).thenReturn(List.of(estadoDeshabilitado));

        // Act
        List<Estado> resultado = estadoService.findByHabilitado(false);

        // Assert
        assertThat(resultado)
            .isNotNull()
            .hasSize(1)
            .allMatch(estado -> !estado.getHabilitado());
        
        verify(estadoRepository, times(1)).findByHabilitado(false);
    }

    // ========================================
    // TEST: Escenarios de validación
    // ========================================

    @Test
    @DisplayName("findAll() debe retornar lista vacía cuando no hay estados")
    void testFindAll_DebeRetornarListaVaciaCuandoNoHayDatos() {
        // Arrange
        when(estadoRepository.findAll()).thenReturn(List.of());

        // Act
        List<Estado> resultado = estadoService.findAll();

        // Assert
        assertThat(resultado)
            .isNotNull()
            .isEmpty();
        
        verify(estadoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Estado debe tener propiedades correctas")
    void testEstado_DebeTenerPropiedadesCorrectas() {
        // Arrange & Act
        Estado nuevoEstado = new Estado();
        nuevoEstado.setIdEstado(5);
        nuevoEstado.setDescripcion("Cancelado");
        nuevoEstado.setHabilitado(false);

        // Assert
        assertThat(nuevoEstado.getIdEstado()).isEqualTo(5);
        assertThat(nuevoEstado.getDescripcion()).isEqualTo("Cancelado");
        assertThat(nuevoEstado.getHabilitado()).isFalse();
    }
}

