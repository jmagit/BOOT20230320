package com.example.domains.entities.dtos;


import com.example.domains.entities.Film;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Value;

@Schema(name = "Pelicula (Corto)", description = "Version corta de las peliculas")
@Value
public class FilmShortDTO {
	@Schema(description = "Identificador de la pelicula", required = true, accessMode = AccessMode.READ_ONLY)
	private int filmId;
	@Schema(description = "Titulo de la pelicula", required = true)
	private String title;
	
	public static FilmShortDTO from(Film source) {
		return new FilmShortDTO(source.getFilmId(), source.getTitle());
	}
}
