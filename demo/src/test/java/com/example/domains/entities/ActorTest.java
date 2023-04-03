package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ActorTest {

	@Test
	void testIsValid() {
		var fixture = new Actor(0, "Pepito", "GRILLO");
		assertTrue(fixture.isValid());
	}

	@DisplayName("El nombre de tener entre 2 y 45 caracteres, y no puede estar en blanco")
	@ParameterizedTest(name = "nombre: -{0}- -> {1}")
	@CsvSource(value = {
			"'','ERRORES: firstName: el tamaño debe estar entre 2 y 45, no debe estar vacío.'", 
			"' ','ERRORES: firstName: no debe estar vacío, el tamaño debe estar entre 2 y 45.'", 
			"'   ','ERRORES: firstName: no debe estar vacío.'", 
			"A,'ERRORES: firstName: el tamaño debe estar entre 2 y 45.'",
			"12345678901234567890123456789012345678901234567890,'ERRORES: firstName: el tamaño debe estar entre 2 y 45.'"})
	void testNombreIsInvalid(String valor, String error) {
		var fixture = new Actor(0, valor, "GRILLO");
		assertTrue(fixture.isInvalid());
		assertEquals(error, fixture.getErrorsMessage());
	}

}
