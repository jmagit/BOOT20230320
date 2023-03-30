package com.example.domains.entities.dtos;

import java.util.List;

import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class NovedadesDTO {
	private List<Film> films;
	private List<Actor> actors;
	private List<Category> categories;
	
}
