package com.grossgym.fitness.repository;

import com.grossgym.fitness.model.Estado;
import com.grossgym.fitness.model.TipoPago;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Tests EDUCATIVOS para TipoPagoRepository
 * 
 * CONCEPTO: Repository con relaciones (@ManyToOne)
 * 
 * TipoPago tiene relación con Estado.
 * En los tests, solo verificamos que las relaciones funcionen.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de TipoPagoRepository - Aprendiendo Relaciones")
class TipoPagoRepositoryTest {

    @Mock
    private TipoPagoRepository tipoPagoRepository;

    private TipoPago tipoPago1;

    @BeforeEach
    void setUp() {
        Estado estado = new Estado();
        estado.setIdEstado(1);
        estado.setDescripcion("Activo");
        
        tipoPago1 = new TipoPago();
        tipoPago1.setIdPago(1);
        tipoPago1.setDescripcion("Efectivo");
        tipoPago1.setEstado(true);  // Relación
    }

    @Test
    @DisplayName("APRENDIENDO: Repository con relaciones @ManyToOne")
    void testSaveConRelacion() {
        // Configurar
        when(tipoPagoRepository.save(any(TipoPago.class))).thenReturn(tipoPago1);

        // Ejecutar
        TipoPago resultado = tipoPagoRepository.save(tipoPago1);

        // Verificar
        assertThat(resultado.getEstado()).isNotNull();
        assertThat(resultado.getEstado()).isEqualTo(true);
        
        // APRENDIZAJE:
        // - TipoPago tiene un Estado asociado (@ManyToOne)
        // - Cuando guardamos TipoPago, también mantenemos su Estado
        // - JPA maneja las relaciones automáticamente
    }

    @Test
    @DisplayName("APRENDIENDO: findByEstado() - Query Method con relación")
    void testFindByEstado() {
        // Configurar
        Estado estadoActivo = new Estado();
        estadoActivo.setIdEstado(1);
        
        when(tipoPagoRepository.findByEstado(any(Boolean.class)))
            .thenReturn(Arrays.asList(tipoPago1));

        // Ejecutar
        List<TipoPago> resultado = tipoPagoRepository.findByEstado(true);

        // Verificar
        assertThat(resultado).hasSize(1);
        
        // APRENDIZAJE:
        // - findByEstado() busca por la entidad relacionada
        // - Spring Data JPA crea el JOIN automáticamente
        // - Formato: findBy + NombreCampoRelacion
    }
}

/*
 * ===============================================
 * CONCEPTO: Relaciones JPA
 * ===============================================
 * 
 * @ManyToOne = Muchos a Uno
 * - Muchos TipoPago pueden tener el mismo Estado
 * - En SQL: Foreign Key
 * 
 * Query Methods con Relaciones:
 * - findByEstado() → busca por la entidad relacionada
 * - Spring crea el JOIN automáticamente
 * 
 * IMPORTANTE:
 * - En tests, creamos objetos completos (con sus relaciones)
 * - JPA maneja las foreign keys automáticamente
 * 
 * ===============================================
 */

