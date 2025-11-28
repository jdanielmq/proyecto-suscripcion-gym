package com.grossgym.fitness.repository;

import com.grossgym.fitness.model.TipoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio para la entidad TipoPago
 * Maneja las operaciones de persistencia de tipos de pago
 */
@Repository
public interface TipoPagoRepository extends JpaRepository<TipoPago, Integer> {
    
    /**
     * Busca tipos de pago por estado
     * @param estado true para activos, false para inactivos
     * @return Lista de tipos de pago
     */
    List<TipoPago> findByEstado(Boolean estado);
    
    /**
     * Busca un tipo de pago por descripción
     * @param descripcion Descripción del tipo de pago
     * @return TipoPago encontrado
     */
    TipoPago findByDescripcion(String descripcion);
}

