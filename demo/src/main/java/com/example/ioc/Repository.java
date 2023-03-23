package com.example.ioc;

import com.example.exceptions.InvalidDataException;

public interface Repository<T> {
	T load();
	void save(T item) throws InvalidDataException;
}
