package com.example.ioc;

public interface Service<K, V> {
	V get(K id);

	void add(V item);

	void modify(V item);

	void remove(K id);
}
