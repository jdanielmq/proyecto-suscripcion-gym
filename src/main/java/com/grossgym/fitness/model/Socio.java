package com.grossgym.fitness.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa a los socios del gimnasio
 */
@Entity
@Table(name = "socio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Socio {


	@Id
    @Column(name = "rut", length = 10)
    private String rut;

    @Column(name = "nombres", nullable = false, length = 200)
    private String nombres;

    @Column(name = "apellido_paterno", nullable = false, length = 200)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", nullable = false, length = 200)
    private String apellidoMaterno;

    @Column(name = "genero", nullable = false, length = 100)
    private String genero;

    @Column(name = "correo", nullable = false, length = 200)
    private String correo;

    @Column(name = "celular", nullable = false, length = 100)
    private String celular;

    @Column(name = "habilitado", nullable = false)
    private Boolean habilitado;

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getRut() {
		return rut;
	}
    public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
    
    
}

