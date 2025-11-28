package com.grossgym.fitness.repository;

import com.grossgym.fitness.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio para la entidad Plan
 * Maneja las operaciones de persistencia de planes de suscripción
 */
@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {
    
    /**
     * Busca planes por tipo
     * @param tipoPlan Tipo de plan (Diario, Mensual, etc.)
     * @return Lista de planes
     */
    List<Plan> findByTipoPlan(String tipoPlan);
    
    /**
     * Busca planes que incluyen matrícula
     * @param isMatricula true para planes con matrícula
     * @return Lista de planes
     */
    List<Plan> findByIsMatricula(Boolean isMatricula);
    
    /**
     * Busca planes por unidad de tiempo
     * @param unidad Unidad (DIA, MES, AÑO)
     * @return Lista de planes
     */
    List<Plan> findByUnidad(String unidad);
}

