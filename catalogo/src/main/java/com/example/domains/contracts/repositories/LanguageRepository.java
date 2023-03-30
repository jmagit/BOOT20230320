package com.example.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domains.entities.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer> {
	List<Language> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
}
