package com.example.ioc;

import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

public interface Service<K, V> {
	V get(K id);

	void add(V item) throws InvalidDataException, NotFoundException;

	void modify(V item) throws InvalidDataException;

	void remove(K id) throws InvalidDataException;
}
