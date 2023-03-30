package com.example;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;
import com.example.domains.entities.Film.Rating;

import jakarta.transaction.Transactional;

import com.example.domains.entities.Language;

@SpringBootApplication
public class CatalogoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}

	@Autowired
	FilmService srv;
	
	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.out.println("------------------> Aplicacion iniciada");
		var peli = new Film("Hola mundo", new Language(2), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0));
//		peli.setRating(Rating.ADULTS_ONLY);
//		peli.addActor(1);
//		peli.addActor(2);
//		peli.addActor(3);
//		peli.addCategory(2);
//		peli.addCategory(4);
//		System.out.println(peli.getErrorsMessage());
//		peli = srv.add(peli);
//		System.out.println(peli.getFilmId());
//		peli = srv.getOne(1001).get();
//		peli.removeActor(new Actor(1));
//		peli.removeActor(new Actor(2));
//		peli.addActor(4);
//		peli.removeCategory(peli.getCategories().get(0));
//		peli.addCategory(1);
//		peli.setTitle("Adios mundo");
//		srv.modify(peli);
//		srv.deleteById(1001);
	}

}
