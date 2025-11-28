package com.grossgym.fitness.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa los planes de suscripción disponibles
 * (Diario, Mensual, Trimestral, Anual, etc.)
 */
@Entity
@Table(name = "plan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan")
    private Integer idPlan;

    @Column(name = "tipo_plan", nullable = false, length = 200)
    private String tipoPlan;

    @Column(name = "monto_plan")
    private Integer montoPlan;

    @Column(name = "duracion")
    private Integer duracion;

    @Column(name = "unidad", nullable = false, length = 100)
    private String unidad;

    @Column(name = "isMatricula", nullable = false)
    private Boolean isMatricula = false;

    @Column(name = "monto_matricula", nullable = false)
    private Integer montoMatricula = 0;

	public void setIdPlan(Integer idPlan) {
		this.idPlan = idPlan;
	}
    
    
}

