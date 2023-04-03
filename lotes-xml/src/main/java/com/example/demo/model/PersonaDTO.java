package com.example.demo.model;

import lombok.Data;

@Data
public class PersonaDTO {
	private long id;
	private String nombre, apellidos, correo, sexo, ip;
}
