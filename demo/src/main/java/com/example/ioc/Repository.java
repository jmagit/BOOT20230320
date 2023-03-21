package com.example.ioc;

public interface Repository<T> {
	T load();
	void save(T item);
}
