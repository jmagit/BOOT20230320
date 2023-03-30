package com.example.domains.contracts.services;

import java.sql.Timestamp;
import java.util.List;

import com.example.domains.core.contracts.services.DomainService;
import com.example.domains.entities.Category;

public interface CategoryService extends DomainService<Category, Integer> {
	List<Category> novedades(Timestamp fecha);
}
