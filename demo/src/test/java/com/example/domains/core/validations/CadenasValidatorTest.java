package com.example.domains.core.validations;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.core.test.SpaceCamelCase;

class CadenasValidatorTest {

	@Nested
	@DisplayName("Pruebas del método IsNIF")
	@DisplayNameGeneration(SpaceCamelCase.class)
	class IsNIF {

		@ParameterizedTest(name = "Caso: {0}")
		@ValueSource(strings = { "12345678z", "12345678Z", "4g" })
		@NullSource
		void casosValidos(String caso) {
			assertTrue(CadenasValidator.isNIF(caso));
		}

		@ParameterizedTest(name = "Caso: {0}")
		@ValueSource(strings = { "12345678", "Z12345678", "kk", "0T" })
		@EmptySource
		void casosInvalidos(String caso) {
			assertFalse(CadenasValidator.isNIF(caso));
		}

		@Test
		void testIsNotNIF() {
			assertTrue(CadenasValidator.isNotNIF(""));
		}
	}
}
