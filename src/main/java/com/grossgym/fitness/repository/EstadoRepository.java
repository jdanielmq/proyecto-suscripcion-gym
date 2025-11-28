package com.grossgym.fitness.repository;

import com.grossgym.fitness.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio para la entidad Estado
 * Maneja las operaciones de persistencia de estados de suscripción
 */
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
    
    /**
     * Busca estados por habilitado
     * @param habilitado true para habilitados
     * @return Lista de estados
     */
    List<Estado> findByHabilitado(Boolean habilitado);
    
    /**
     * Busca un estado por descripción
     * @param descripcion Descripción del estado
     * @return Estado encontrado
     */
    Estado findByDescripcion(String descripcion);
}

