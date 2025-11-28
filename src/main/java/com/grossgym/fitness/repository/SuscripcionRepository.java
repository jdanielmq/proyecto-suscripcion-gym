package com.grossgym.fitness.repository;

import com.grossgym.fitness.model.Suscripcion;
import com.grossgym.fitness.model.Socio;
import com.grossgym.fitness.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio para la entidad Suscripcion
 * Maneja las operaciones de persistencia de suscripciones
 */
@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion, Integer> {
    
    /**
     * Busca suscripciones por socio
     * @param socio Socio del que se buscan las suscripciones
     * @return Lista de suscripciones
     */
    List<Suscripcion> findBySocio(Socio socio);
    
    /**
     * Busca suscripciones por estado
     * @param estado Estado de la suscripción
     * @return Lista de suscripciones
     */
    List<Suscripcion> findByEstado(Estado estado);
    
    /**
     * Busca suscripciones por número de transacción
     * @param nroTransaccion Número de transacción
     * @return Suscripción encontrada
     */
    Suscripcion findByNroTransaccion(String nroTransaccion);
    
    /**
     * Busca suscripciones vigentes (fecha término mayor a la fecha actual)
     * @param fecha Fecha actual
     * @return Lista de suscripciones vigentes
     */
    @Query("SELECT s FROM Suscripcion s WHERE s.fechaTermino > ?1")
    List<Suscripcion> findSuscripcionesVigentes(LocalDateTime fecha);
    
    /**
     * Busca suscripciones por socio y estado
     * @param socio Socio
     * @param estado Estado
     * @return Lista de suscripciones
     */
    List<Suscripcion> findBySocioAndEstado(Socio socio, Estado estado);
}

