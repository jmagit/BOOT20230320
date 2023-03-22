package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculadoraTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSumaPositivoPositivo() {
		var calc = new Calculadora();
		
		var rslt = calc.suma(2, 2);
		
		assertEquals(4, rslt);
	}

	@Test
	void testSumaPositivoNegativo() {
		var calc = new Calculadora();
		
		var rslt = calc.suma(1, -0.9);
		
		assertEquals(0.1, rslt);
	}

	@Test
	void testSumaNegativoPositivo() {
		var calc = new Calculadora();
		
		var rslt = calc.suma(-1, 5);
		
		assertEquals(4, rslt);
	}

	@Test
	void testSumaNegativoNegativo() {
		var calc = new Calculadora();
		
		var rslt = calc.suma(-1, -5);
		
		assertEquals(-6, rslt);
	}

	@Test
	void testSumaDecimale() {
		var calc = new Calculadora();
		
		var rslt = calc.suma(0.1, 0.2);
		
		assertEquals(0.3, rslt);
	}

	@Test
	void testDividir() {
		var calc = new Calculadora();
		
		var rslt = calc.divide(1, 0.0);
		
		assertEquals(0.5, rslt);
	}

}
