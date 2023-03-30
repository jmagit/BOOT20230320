package com.example.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.domains.core.contracts.repositories.RepositoryWithProjections;
import com.example.domains.entities.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor>, RepositoryWithProjections {
	List<Actor> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
}
