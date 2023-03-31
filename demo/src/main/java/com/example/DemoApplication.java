package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;
import com.example.ioc.EjemplosIoC;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

//	@Autowired
//	ActorRepository dao;
	
//	@Autowired
//	ActorService srv;
	
	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.out.println("Aplicación arrancada");
//		(new EjemplosIoC()).run();
//		var actor = new Actor(0, "Pepito", "grillo");
//		dao.save(actor);
//		dao.deleteById(215);
//		var item = dao.findById(215);
//		if(item.isPresent()) {
//			var actor = item.get();
//			actor.setLastName(actor.getLastName().toUpperCase());
//			dao.save(actor);
//			dao.findAll().forEach(System.out::println);
//		} else {
//			System.out.println("Actor no encontrado");
//		}
//		dao.findTop5ByFirstNameStartingWithOrderByLastNameDesc("P")
//			.forEach(System.out::println);
//		dao.findTop5ByFirstNameStartingWith("P", Sort.by("LastName").descending())
//			.forEach(System.out::println);
//		dao.findTop5ByFirstNameStartingWith("P", Sort.by("FirstName"))
//		.forEach(System.out::println);
//		dao.findConJPQL().forEach(System.out::println);
//		dao.findConJPQL(5).forEach(System.out::println);
//		dao.findConSQL(5).forEach(System.out::println);
//		dao.findAll((root, query, builder) -> builder.lessThan(root.get("actorId"), 5))
//			.forEach(System.out::println);
//		dao.findAll((root, query, builder) -> builder.greaterThan(root.get("actorId"), 200))
//			.forEach(System.out::println);
//		var item = dao.findById(1);
//		if(item.isPresent()) {
//			var actor = item.get();
//			System.out.println(actor);
//			actor.getFilmActors()
//			.forEach(o -> System.out.println(o.getFilm().getTitle()));
//		} else {
//			System.out.println("Actor no encontrado");
//		}
//		var rslt = dao.findAll(PageRequest.of(1, 20, Sort.by("actorId")));
//		rslt.getContent().stream().map(item -> ActorDTO.from(item)).forEach(System.out::println);
//		dao.findByActorIdNotNull().forEach(System.out::println);
//		dao.findByActorIdNotNull().forEach(item->System.out.println(item.getActorId() + " " + item.getNombre()));
//		dao.findAllBy(ActorShort.class).forEach(item->System.out.println(item.getActorId() + " " + item.getNombre()));
//		dao.findAllBy(ActorDTO.class).forEach(System.out::println);
//		var actor = new Actor(0, "4", "d");
////		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
////		var err = validator.validate(actor);
////		if(err.size() > 0) {
////			err.forEach(e -> System.out.println(e.getPropertyPath() + ": " + e.getMessage()));
////		} else 
////			dao.save(actor);
//		if(actor.isInvalid()) {
//			System.out.println(actor.getErrorsMessage());
//			actor.getErrorsFields().forEach((fld, err) -> System.out.println(fld + ": " + err));
//		} 
//		else 
//			dao.save(actor);
//		ObjectMapper objectMapper = new ObjectMapper();
//		dao.findAllBy(ActorDTO.class).stream().map(
//				item -> {
//					try {
//						return objectMapper.writeValueAsString(item);
//					} catch (JsonProcessingException e) {
//						return "";
//					}
//				}).forEach(System.out::println);
//		srv.add(new Actor(0, "4", "d"));
//		srv.add(new Actor(1, "KK", "KKK"));
	}
}
