package com.example.application.resources;

import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.Map;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;
import com.example.domains.entities.dtos.ElementoDto;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/actores")
public class ActorResource {
	@Autowired
	private ActorService srv;

	@GetMapping
	public List<ActorShort> getAll(@RequestParam(required = false) String filter) {
		if (filter != null) {}
		
		return srv.getByProjection(ActorShort.class);
	}

	@GetMapping(params = "page")
	public Page<ActorDTO> getAll(Pageable pageable) {
		return srv.getByProjection(pageable, ActorDTO.class);
	}

	@GetMapping(path = "/{id}")
	public ActorDTO getOne(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return ActorDTO.from(item.get());
	}
	
	@GetMapping(path = "/{id}/pelis")
	public List<ElementoDto<Integer, String>> getPelis(@PathVariable int id) throws NotFoundException {
		return srv.getOne(id).get().getFilmActors().stream()
				.map(f-> new ElementoDto<>(f.getFilm().getFilmId(), f.getFilm().getTitle()))
				.toList();
	}

	@GetMapping(path = "/{id}/peliculas")
	public List<Map<String, String>> getPeliculas(@PathVariable int id) throws NotFoundException {
		return srv.getOne(id).get().getFilmActors().stream()
				.map(f-> Map.of("id", Integer.toString(f.getFilm().getFilmId()), "titulo", f.getFilm().getTitle()))
				.toList();
	}
	
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody ActorDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		var newItem = srv.add(ActorDTO.from(item));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newItem.getActorId()).toUri();
		return ResponseEntity.created(location).build();
	}
	@PostMapping("/premios")
	public String darPremio(@RequestParam String premio) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		//srv.darPremios(null);
		return "He dado el premio " + premio;
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody ActorDTO item) throws BadRequestException, NotFoundException, InvalidDataException {
		if(id != item.getActorId())
			throw new BadRequestException("No coinciden los identificadores");
		srv.modify(ActorDTO.from(item));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}
	
	
}

