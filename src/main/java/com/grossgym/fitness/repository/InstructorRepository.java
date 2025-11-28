package com.grossgym.fitness.repository;

import com.grossgym.fitness.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio para la entidad Instructor
 * Maneja las operaciones de persistencia de instructores
 */
@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    
    /**
     * Busca instructores por estado habilitado
     * @param habilitado true para habilitados
     * @return Lista de instructores
     */
    List<Instructor> findByHabilitado(Boolean habilitado);
    
    /**
     * Busca instructores por nombre (contiene)
     * @param nombre Nombre del instructor
     * @return Lista de instructores
     */
    List<Instructor> findByNombreInstructorContaining(String nombre);
}

