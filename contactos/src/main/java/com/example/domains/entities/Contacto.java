package com.example.domains.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import io.swagger.v3.oas.annotations.media.Schema;

@Document("contactos")
@Schema(description = "Datos de la persona de contacto")
public class Contacto {
	@Id
	private String id;
	private String tratamiento;
	@NotBlank
	@Size(min=2, max=50)
	private String nombre;
	@Size(min=2, max=50)
	private String apellidos;
	@Pattern(regexp = "^(\\d{3}\\s){2}\\d{3}$")
	private String telefono;
	@Email
	private String email;
	@Pattern(regexp = "[HM]")
	private String sexo;
	@Past
	private LocalDate nacimiento;
	@URL
	private String avatar;
	private boolean conflictivo = false;
	private String icono;
	private List<Direccion> direcciones;

	public Contacto() {
		super();
		direcciones = new ArrayList<>();
	}
	public Contacto(String id, String tratamiento, @NotBlank @Size(min = 2, max = 2)String nombre,
			@Size(min = 2, max = 2) String apellidos, @Pattern(regexp = "^(\\d{3}\\s){2}\\d{3}$") String telefono,
			@Email String email, @Pattern(regexp = "[HM]") String sexo, @Past LocalDate nacimiento, @URL String avatar,
			boolean conflictivo, String icono) {
		this();
		this.id = id;
		this.tratamiento = tratamiento;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.email = email;
		this.sexo = sexo;
		this.nacimiento = nacimiento;
		this.avatar = avatar;
		this.conflictivo = conflictivo;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTratamiento() {
		return tratamiento;
	}
	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public LocalDate getNacimiento() {
		return nacimiento;
	}
	public void setNacimiento(LocalDate nacimiento) {
		this.nacimiento = nacimiento;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public boolean isConflictivo() {
		return conflictivo;
	}
	public void setConflictivo(boolean conflictivo) {
		this.conflictivo = conflictivo;
	}
	
	public String getIcono() {
		return icono;
	}
	public void setIcono(String icono) {
		this.icono = icono;
	}
	public List<Direccion> getDirecciones() {
		return direcciones;
	}
	public void setDirecciones(List<Direccion> direcciones) {
		this.direcciones = direcciones;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contacto other = (Contacto) obj;
		return id.equals(other.id);
	}
	@Override
	public String toString() {
		return "Contacto [id=" + id + ", tratamiento=" + tratamiento + ", nombre=" + nombre + ", apellidos=" + apellidos
				+ ", telefono=" + telefono + ", email=" + email + ", sexo=" + sexo + ", nacimiento=" + nacimiento
				+ ", avatar=" + avatar + ", conflictivo=" + conflictivo + "]";
	}	
		
}
