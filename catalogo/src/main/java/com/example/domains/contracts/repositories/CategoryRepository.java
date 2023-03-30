package com.example.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domains.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	List<Category> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
}
