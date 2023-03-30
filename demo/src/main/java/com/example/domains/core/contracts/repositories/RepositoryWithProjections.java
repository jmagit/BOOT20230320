package com.example.domains.core.contracts.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface RepositoryWithProjections {
	<T> List<T> findAllBy(Class<T> tipo);
	<T> Iterable<T> findAllBy(Sort orden, Class<T> tipo);
	<T> Page<T> findAllBy(Pageable page, Class<T> tipo);
}
