package com.grossgym.fitness.service.impl;

import com.grossgym.fitness.model.Socio;
import com.grossgym.fitness.repository.SocioRepository;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios para SocioServiceImpl
 * Usa JUnit 5 + Mockito + AssertJ
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Servicio de Socios")
class SocioServiceImplTest {

    @Mock
    private SocioRepository socioRepository;

    @InjectMocks
    private SocioServiceImpl socioService;

    private Socio socio1;
    private Socio socio2;

    @BeforeEach
    void setUp() {
        // Arrange - Preparar datos de prueba
        socio1 = new Socio();
        socio1.setRut("12345678-9");
        socio1.setNombres("Juan Pablo");
        socio1.setApellidoPaterno("Soto");
        socio1.setApellidoMaterno("Vargas");
        socio1.setGenero("Masculino");
        socio1.setCorreo("juan@example.com");
        socio1.setCelular("+56912345678");
        socio1.setHabilitado(true);

        socio2 = new Socio();
        socio2.setRut("98765432-1");
        socio2.setNombres("María Isabel");
        socio2.setApellidoPaterno("Silva");
        socio2.setApellidoMaterno("Torres");
        socio2.setGenero("Femenino");
        socio2.setCorreo("maria@example.com");
        socio2.setCelular("+56987654321");
        socio2.setHabilitado(true);
    }

    @Test
    @DisplayName("findAll() debe retornar lista de socios")
    void testFindAll_DebeRetornarListaDeSocios() {
        // Arrange
        List<Socio> socios = Arrays.asList(socio1, socio2);
        when(socioRepository.findAll()).thenReturn(socios);

        // Act
        List<Socio> resultado = socioService.findAll();

        // Assert
        assertThat(resultado)
            .isNotNull()
            .hasSize(2)
            .containsExactlyInAnyOrder(socio1, socio2);
        
        verify(socioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById() debe retornar socio cuando existe")
    void testFindById_DebeRetornarSocioCuandoExiste() {
        // Arrange
        String rut = "12345678-9";
        when(socioRepository.findById(rut)).thenReturn(Optional.of(socio1));

        // Act
        Optional<Socio> resultado = socioService.findById(rut);

        // Assert
        assertThat(resultado)
            .isPresent()
            .contains(socio1);
        
        verify(socioRepository, times(1)).findById(rut);
    }

    @Test
    @DisplayName("findById() debe retornar Optional.empty() cuando no existe")
    void testFindById_DebeRetornarVacioCuandoNoExiste() {
        // Arrange
        String rut = "99999999-9";
        when(socioRepository.findById(rut)).thenReturn(Optional.empty());

        // Act
        Optional<Socio> resultado = socioService.findById(rut);

        // Assert
        assertThat(resultado).isEmpty();
        
        verify(socioRepository, times(1)).findById(rut);
    }

    @Test
    @DisplayName("save() debe guardar y retornar el socio")
    void testSave_DebeGuardarYRetornarSocio() {
        // Arrange
        when(socioRepository.save(any(Socio.class))).thenReturn(socio1);

        // Act
        Socio resultado = socioService.save(socio1);

        // Assert
        assertThat(resultado)
            .isNotNull()
            .isEqualTo(socio1);
        assertThat(resultado.getRut()).isEqualTo("12345678-9");
        assertThat(resultado.getNombres()).isEqualTo("Juan Pablo");
        
        verify(socioRepository, times(1)).save(socio1);
    }

    @Test
    @DisplayName("deleteById() debe eliminar socio")
    void testDeleteById_DebeEliminarSocio() {
        // Arrange
        String rut = "12345678-9";
        doNothing().when(socioRepository).deleteById(rut);

        // Act
        socioService.deleteById(rut);

        // Assert
        verify(socioRepository, times(1)).deleteById(rut);
    }

    @Test
    @DisplayName("findByHabilitado() debe retornar socios habilitados")
    void testFindByHabilitado_DebeRetornarSociosHabilitados() {
        // Arrange
        List<Socio> sociosHabilitados = Arrays.asList(socio1, socio2);
        when(socioRepository.findByHabilitado(true)).thenReturn(sociosHabilitados);

        // Act
        List<Socio> resultado = socioService.findByHabilitado(true);

        // Assert
        assertThat(resultado)
            .isNotNull()
            .hasSize(2)
            .allMatch(socio -> socio.getHabilitado());
        
        verify(socioRepository, times(1)).findByHabilitado(true);
    }

    @Test
    @DisplayName("findByNombresContaining() debe retornar socios que coincidan")
    void testFindByNombresContaining_DebeRetornarSociosQueCoincidan() {
        // Arrange
        String nombre = "Juan";
        List<Socio> socios = List.of(socio1);
        when(socioRepository.findByNombresContaining(anyString())).thenReturn(socios);

        // Act
        List<Socio> resultado = socioService.findByNombresContaining(nombre);

        // Assert
        assertThat(resultado)
            .isNotNull()
            .hasSize(1)
            .contains(socio1);
        
        verify(socioRepository, times(1)).findByNombresContaining(nombre);
    }

    @Test
    @DisplayName("save() debe actualizar socio existente")
    void testSave_DebeActualizarSocioExistente() {
        // Arrange
        socio1.setCorreo("nuevo.correo@example.com");
        when(socioRepository.save(any(Socio.class))).thenReturn(socio1);

        // Act
        Socio resultado = socioService.save(socio1);

        // Assert
        assertThat(resultado)
            .isNotNull()
            .isEqualTo(socio1);
        assertThat(resultado.getCorreo()).isEqualTo("nuevo.correo@example.com");
        
        verify(socioRepository, times(1)).save(socio1);
    }
}

