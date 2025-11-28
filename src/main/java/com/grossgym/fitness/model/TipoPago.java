package com.grossgym.fitness.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa los tipos de pago disponibles en el sistema
 * (Débito, Crédito, Efectivo, Transferencia, etc.)
 */
@Entity
@Table(name = "tipo_pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Integer idPago;

    @Column(name = "descripcion", nullable = false, length = 200)
    private String descripcion;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

	public void setIdPago(Integer idPago) {
		this.idPago = idPago;
	}
    
    
}

