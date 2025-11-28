package com.grossgym.fitness.repository;

import com.grossgym.fitness.model.Inbody;
import com.grossgym.fitness.model.Socio;
import com.grossgym.fitness.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio para la entidad Inbody
 * Maneja las operaciones de persistencia de evaluaciones InBody
 */
@Repository
public interface InbodyRepository extends JpaRepository<Inbody, Long> {
    
    /**
     * Busca evaluaciones por socio
     * @param socio Socio del que se buscan las evaluaciones
     * @return Lista de evaluaciones
     */
    List<Inbody> findBySocio(Socio socio);
    
    /**
     * Busca evaluaciones por instructor
     * @param instructor Instructor que realizó la evaluación
     * @return Lista de evaluaciones
     */
    List<Inbody> findByInstructor(Instructor instructor);
    
    /**
     * Busca evaluaciones por estado habilitado
     * @param habilitado true para habilitadas
     * @return Lista de evaluaciones
     */
    List<Inbody> findByHabilitado(Boolean habilitado);
    
    /**
     * Busca evaluaciones por socio y estado habilitado
     * @param socio Socio
     * @param habilitado Estado
     * @return Lista de evaluaciones
     */
    List<Inbody> findBySocioAndHabilitado(Socio socio, Boolean habilitado);
    
    /**
     * Busca evaluaciones por rango de fechas
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha de fin
     * @return Lista de evaluaciones
     */
    List<Inbody> findByFechaEvaluacionBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}

