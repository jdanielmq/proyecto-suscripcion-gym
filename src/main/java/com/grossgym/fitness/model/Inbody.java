package com.grossgym.fitness.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entidad que representa las evaluaciones InBody de los socios
 */
@Entity
@Table(name = "inbody")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inbody {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inbody")
    private Long idInbody;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_socio", nullable = false)
    private Socio socio;

    @Column(name = "habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "json_inbody", nullable = false, columnDefinition = "json")
    private String jsonInbody;

    @Column(name = "fecha_evaluacion", nullable = false)
    private LocalDateTime fechaEvaluacion;

    @Column(name = "comentario", length = 100)
    private String comentario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_instructor")
    private Instructor instructor;

	public void setIdInbody(Long idInbody) {
		this.idInbody = idInbody;
	}
    
    
}

