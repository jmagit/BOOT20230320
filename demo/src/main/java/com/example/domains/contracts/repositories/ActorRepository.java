package com.example.domains.contracts.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.domains.entities.Actor;


public interface ActorRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor> {
	List<Actor> findTop5ByFirstNameStartingWithOrderByLastNameDesc(String prefijo);
	List<Actor> findTop5ByFirstNameStartingWith(String prefijo, Sort orden);
//	List<Actor> findTop5ByNombre(String prefijo, Sort orden);

	@Query("SELECT a FROM Actor a WHERE a.actorId < :id")
	List<Actor> findConJPQL(@Param("id") int actorId);
	@Query(name = "Actor.findAll")
	List<Actor> findConJPQL();
	@Query(value = "SELECT * from actor WHERE actor_id < ?1", nativeQuery = true)
	List<Actor> findConSQL(int actorId);
}
