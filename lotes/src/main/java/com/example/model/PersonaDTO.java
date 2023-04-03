package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class PersonaDTO {
	private long id;
	private String nombre, apellidos, correo, sexo, ip;
}
