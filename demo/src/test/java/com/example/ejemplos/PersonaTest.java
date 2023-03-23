package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.example.core.test.Smoke;

class PersonaTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@Smoke
	void testCreate() {
		var p = Persona.builder().id(1).nombre("Pepito").apellidos("Grillo").build();
//		p = null;
//		assertNotNull(p);
		assertTrue(p instanceof Persona, "No es instancia de persona");
		assertAll("Validar propiedades",
			() -> assertEquals(1, p.getId(), "id"),
			() -> assertEquals("Pepito", p.getNombre(), "getNombre"),
			() -> assertEquals("Grillo", p.getApellidos(), "getApellidos")
		);
	}
	@RepeatedTest(value = 5, name = "{displayName} {currentRepetition}/{totalRepetitions}")
	void repeatedTest(RepetitionInfo repetitionInfo) {
		var p = Persona.builder()
				.id(repetitionInfo.getCurrentRepetition())
				.nombre("Pepito" + repetitionInfo.getCurrentRepetition()) // (repetitionInfo.getCurrentRepetition() % 3 == 0 ? "" : repetitionInfo.getCurrentRepetition()))
				.apellidos("Grillo").build();
//		p = null;
//		assertNotNull(p);
		assertTrue(p instanceof Persona, "No es instancia de persona");
		assertAll("Validar propiedades",
			() -> assertEquals(repetitionInfo.getCurrentRepetition(), p.getId(), "id"),
			() -> assertEquals("Pepito" + repetitionInfo.getCurrentRepetition(), p.getNombre(), "getNombre"),
			() -> assertEquals("Grillo", p.getApellidos(), "getApellidos")
		);
	}

}
