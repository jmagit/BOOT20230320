package com.example.domains.entities.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Pelicula (Editar)", description = "Version editable de las películas")
@Data @AllArgsConstructor @NoArgsConstructor
public class FilmEditDTO {
	@Schema(description = "Identificador de la película", required = true, accessMode = AccessMode.READ_ONLY)
	private int filmId;
	@Schema(description = "Una breve descripción o resumen de la trama de la película", minLength = 2)
	private String description;
	@Schema(description = "La duración de la película, en minutos", required = true)
	private Integer length;
	@Schema(description = "La clasificación por edades asignada a la película", allowableValues = {"G", "PG", "PG-13", "R", "NC-17"})
	@Pattern(regexp = "^(G|PG|PG-13|R|NC-17)$")
	private String rating;
	@Schema(description = "El año en que se estrenó la película")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
	private Short releaseYear;
	@Schema(description = "La duración del período de alquiler, en días")
	private Byte rentalDuration;
	@Schema(description = "El coste de alquilar la película por el período establecido")
	private BigDecimal rentalRate;
	@Schema(description = "El importe cobrado al cliente si la película no se devuelve o se devuelve en un estado dañado")
	private BigDecimal replacementCost;
	@Schema(description = "El título de la película", required = true)
	@NotBlank
	@Size(min=2, max = 128)
	private String title;
	@Schema(description = "El identificador del idioma de la película")
	@NotNull
	private Integer languageId;
	@Schema(description = "El identificador del idioma original de la película")
	private Integer languageVOId;
	@Schema(description = "La lista de identificadores de actores que participan en la película")
	private List<Integer> actors = new ArrayList<Integer>();
	@Schema(description = "La lista de identificadores de categorías asignadas a la película")
	@ArraySchema(uniqueItems = true, minItems = 1, maxItems = 3)
	private List<Integer> categories = new ArrayList<Integer>();

 	public static FilmEditDTO from(Film source) {
		return new FilmEditDTO(
				source.getFilmId(), 
				source.getDescription(),
				source.getLength(),
				source.getRating() == null ? null : source.getRating().getValue(),
				source.getReleaseYear(),
				source.getRentalDuration(),
				source.getRentalRate(),
				source.getReplacementCost(),
				source.getTitle(),
				source.getLanguage() == null ? null : source.getLanguage().getLanguageId(),
				source.getLanguageVO() == null ? null : source.getLanguageVO().getLanguageId(),
				source.getActors().stream().map(item -> item.getActorId())
					.collect(Collectors.toList()),
				source.getCategories().stream().map(item -> item.getCategoryId())
					.collect(Collectors.toList())
				);
	}
	public static Film from(FilmEditDTO source) {
		Film rslt = new Film(
				source.getFilmId(), 
				source.getTitle(),
				source.getDescription(),
				source.getReleaseYear(),
				source.getLanguageId() == null ? null : new Language(source.getLanguageId()),
				source.getLanguageVOId() == null ? null : new Language(source.getLanguageVOId()),
				source.getRentalDuration(),
				source.getRentalRate(),
				source.getLength(),
				source.getReplacementCost(),
				source.getRating() == null ? null : Film.Rating.getEnum(source.getRating())
				);
		source.getActors().stream().forEach(item -> rslt.addActor(item));
		source.getCategories().stream().forEach(item -> rslt.addCategory(item));
		return rslt;
	}

}
