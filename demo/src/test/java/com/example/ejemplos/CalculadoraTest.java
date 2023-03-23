package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CalculadoraTest {
	Calculadora calc;

	@BeforeEach
	void setUp() throws Exception {
		calc = new Calculadora();
	}

	@Nested
	class Suma {
		@Nested
		class OK {
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
			void testSumaDecimales() {
				var calc = new Calculadora();

				var rslt = calc.suma(0.1, 0.2);

				assertEquals(0.3, rslt);
			}

		}

		@Nested
		class KO {

		}

	}

	@Nested
	class Divide {
		@Nested
		class OK {
			@Test
			void testDividirPorCero() {
				var calc = new Calculadora();

				var rslt = calc.divide(1, 0.0);

				assertEquals(Double.POSITIVE_INFINITY, rslt);
			}

		}

		@Nested
		class KO {

		}

	}

}
