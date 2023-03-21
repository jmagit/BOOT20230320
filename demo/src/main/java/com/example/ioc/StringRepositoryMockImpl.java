package com.example.ioc;

import org.springframework.stereotype.Repository;

@Repository
public class StringRepositoryMockImpl implements StringRepository {

	@Override
	public String load() {
		return "Soy el doble de pruebas de StringRepository";
	}

	@Override
	public void save(String item) {
		System.out.println("No guardo el item: " + item);
	}

}
