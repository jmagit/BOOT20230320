package com.example.ioc;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.exceptions.ArgumentoInvalidoException;
import com.example.exceptions.InvalidDataException;

@Repository
@Primary
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE) 
public class StringRepositoryImpl implements StringRepository {
	private String ultimo = "";
	
	@Override
	public String load() {
		return "Soy el StringRepositoryImpl";
	}

	@Override
	public void save(String item) throws InvalidDataException {
		if(item == "")
			throw new InvalidDataException("La cadena no puede estar vacia");
//		throw new ArgumentoInvalidoException();
		System.out.println("Anterior: " + ultimo);
		this.ultimo = item;
		System.out.println("Guardo el item: " + item);
	}

}
