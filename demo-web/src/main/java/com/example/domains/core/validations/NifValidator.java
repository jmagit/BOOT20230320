package com.example.domains.core.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NifValidator implements ConstraintValidator<NIF, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return CadenasValidator.isNIF(value);
	}
}
