package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.example.core.test.Smoke;
import com.example.core.test.SpaceCamelCase;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class CalculadoraTest {
	Calculadora calc;

	@BeforeEach
	void setUp() throws Exception {
		calc = new Calculadora();
	}

	@Nested
	@DisplayName("Pruebas del método Suma")
	@DisplayNameGeneration(SpaceCamelCase.class)
	class Suma {
		@BeforeEach
		void setUp() throws Exception {
			calc = new Calculadora();
		}
		@Nested
		class OK {
			@Smoke
			@Test
			void test_Suma_Positivo_Positivo() {
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
			@Test
			//@Disabled
			void testSumaMultiple() {
				assertEquals(2, calc.suma(1,1));
				assertEquals(0, calc.suma(-1,1));
				assertEquals(0, calc.suma(1,-1));
				assertEquals(-2, calc.suma(-1,-1));
				assertEquals(0, calc.suma(0,0));
				assumeTrue(false, "La tengo a medias");
			}
			
			@ParameterizedTest(name = "{0} + {1} = {2}")
			@CsvSource(value = {"1,1,2", "0.1,0.2,0.3", "0,0,0", "-1,1,0","1,-1,0", "-1,-1,-2"})
			void testSumasOK(double op1, double op2, double rslt) {
				assertEquals(rslt, calc.suma(op1, op2));
			}
			
			@Test
			void testSumaMock() {
				Calculadora calc = mock(Calculadora.class);
				when(calc.suma(anyDouble(), anyDouble())).thenReturn(3.0).thenReturn(1.0);
				assertEquals(3, calc.suma(2,2));
				assertEquals(1, calc.suma(1,1));
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

				var rslt = calc.divide(1, 1);

				assertEquals(1, rslt);
			}

		}

		@Nested
		class KO {
			@Test
			void testDividirPorCero() {
				var calc = new Calculadora();

				assertThrows(ArithmeticException.class, () -> calc.divide(1, 0.0));
			}

		}

	}

}
