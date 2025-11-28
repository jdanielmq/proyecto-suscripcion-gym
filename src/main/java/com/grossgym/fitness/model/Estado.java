package com.grossgym.fitness.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa los estados de las suscripciones
 * (Activo, Vencido, Suspendido, Cancelado, etc.)
 */
@Entity
@Table(name = "estado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Integer idEstado;

    @Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;

    @Column(name = "habilitado", nullable = false)
    private Boolean habilitado;

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}
    
    
}

