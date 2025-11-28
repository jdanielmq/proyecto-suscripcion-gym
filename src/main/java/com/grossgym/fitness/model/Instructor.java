package com.grossgym.fitness.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa a los instructores del gimnasio
 */
@Entity
@Table(name = "instructor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_instructor")
    private Integer idInstructor;

    @Column(name = "nombre_instructor", nullable = false, length = 200)
    private String nombreInstructor;

    @Column(name = "habilitado", nullable = false)
    private Boolean habilitado;
}

