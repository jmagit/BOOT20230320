package com.example.domains.entities;

import jakarta.validation.constraints.Pattern;

import org.springframework.data.mongodb.core.mapping.Field;

public class Direccion {
	private String calle;
	@Field("codigo")
	@Pattern(regexp = "^\\d{4,5}$")
	private String codigoPostal;
	private String provincia;
	private String pais;
	
	public Direccion() { }
	public Direccion(String calle, String codigoPostal, String provincia, String pais) {
		super();
		this.calle = calle;
		this.codigoPostal = codigoPostal;
		this.provincia = provincia;
		this.pais = pais;
	}
	
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	
	
	
}
