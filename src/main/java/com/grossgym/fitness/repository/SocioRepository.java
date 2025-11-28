package com.grossgym.fitness.repository;

import com.grossgym.fitness.model.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio para la entidad Socio
 * Maneja las operaciones de persistencia de socios del gimnasio
 */
@Repository
public interface SocioRepository extends JpaRepository<Socio, String> {
    
    /**
     * Busca socios por estado habilitado
     * @param habilitado true para habilitados
     * @return Lista de socios
     */
    List<Socio> findByHabilitado(Boolean habilitado);
    
    /**
     * Busca socios por correo
     * @param correo Correo electrónico del socio
     * @return Socio encontrado
     */
    Socio findByCorreo(String correo);
    
    /**
     * Busca socios por nombre (contiene)
     * @param nombres Nombres del socio
     * @return Lista de socios
     */
    List<Socio> findByNombresContaining(String nombres);
    
    /**
     * Busca socios por apellido paterno (contiene)
     * @param apellidoPaterno Apellido paterno del socio
     * @return Lista de socios
     */
    List<Socio> findByApellidoPaternoContaining(String apellidoPaterno);
}

