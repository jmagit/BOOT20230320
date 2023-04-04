package com.example.domains.entities.dtos;

import lombok.Value;

@Value
public class ElementoDto<K, V> {
	K key;
	V value;
}
