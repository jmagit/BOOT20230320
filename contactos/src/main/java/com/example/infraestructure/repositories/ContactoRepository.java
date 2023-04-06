package com.example.infraestructure.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.domains.entities.Contacto;

public interface ContactoRepository extends MongoRepository<Contacto, String> {
	List<Contacto> findByConflictivoTrue();
}
