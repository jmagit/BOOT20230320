package com.example.core.test;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayNameGenerator;

public class SpaceCamelCase extends DisplayNameGenerator.Standard {
	@Override
	public String generateDisplayNameForClass(Class<?> testClass) {
		return spaceCamelCase(super.generateDisplayNameForClass(testClass));
	}
	@Override
	public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
		return spaceCamelCase(super.generateDisplayNameForNestedClass(nestedClass));
	}
	@Override
	public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
		return this.spaceCamelCase(testMethod.getName()) 
				+ DisplayNameGenerator.parameterTypesAsString(testMethod);
	}
	private String spaceCamelCase(String name) {
		StringBuilder result = new StringBuilder();
		result.append(Character.toUpperCase(name.charAt(0)));
		for(char c : name.substring(1).toCharArray())
			result.append(Character.isUpperCase(c) ? (" " + Character.toLowerCase(c)) : c);
		return result.toString();
	}
}

