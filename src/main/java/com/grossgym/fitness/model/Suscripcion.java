package com.grossgym.fitness.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entidad que representa las suscripciones de los socios
 */
@Entity
@Table(name = "suscripcion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Suscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_suscripcion")
    private Integer idSuscripcion;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "nro_transaccion", nullable = false, length = 100)
    private String nroTransaccion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_pago", nullable = false)
    private TipoPago tipoPago;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_plan", nullable = false)
    private Plan plan;

    @Column(name = "monto_plan", nullable = false)
    private Integer montoPlan;

    @Column(name = "monto_matricula", nullable = false)
    private Integer montoMatricula;

    @Column(name = "nro_cuotas")
    private Integer nroCuotas;

    @Column(name = "fecha_termino")
    private LocalDateTime fechaTermino;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estado")
    private Estado estado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_socio", referencedColumnName = "rut", nullable = false)
    private Socio socio;

	public void setIdSuscripcion(Integer idSuscripcion) {
		this.idSuscripcion = idSuscripcion;
	}
    
    
}

