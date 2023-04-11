package com.example.domains.entities.dtos;

import org.springframework.data.rest.core.config.Projection;

import com.example.domains.entities.Film;

@Projection(types = Film.class, name = "titulo")
public interface PelisShort {
	int getFilmId();
	String getTitle();
}
