package com.example.application.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.example.domains.entities.Contacto;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.example.infraestructure.repositories.ContactoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Value;

@RestController
@RequestMapping(path = "/api/v1/contactos")
public class ContactoResource {
	@Autowired
	private ContactoRepository dao;

	@GetMapping
	@Operation(summary = "Listar todos")
	public List<Contacto> getAll() {
		return dao.findAll();
	}
	
	@GetMapping(params = "_page")
	public Page<Contacto> getAll(@ParameterObject @PageableDefault(size = 10) Pageable page) {
		return dao.findAll(page);
	}

	@GetMapping(path = "/{id}")
	@Operation(summary = "Consultar un contacto")
//	@Secured({ "ROLE_ADMIN" })
//	@SecurityRequirement(name = "bearerAuth")
	public Contacto getOne(@PathVariable String id) throws Exception {
		Optional<Contacto> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return rslt.get();
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@Operation(summary = "Creación de un contacto")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<Object> add(@Valid @RequestBody Contacto item) throws Exception {
		if (item.getId() != null && dao.findById(item.getId()).isPresent())
			throw new InvalidDataException("Duplicate key");
		item.setId(null);
		dao.save(item); // ConstraintViolationException
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(item.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@Secured({ "ROLE_USER" })
	@PreAuthorize("authenticated")
	@PutMapping(path = "/{id}")
	@Operation(summary = "Modificación de un contacto")
	@SecurityRequirement(name = "bearerAuth")
	public Contacto modify(@PathVariable String id, @Valid @RequestBody Contacto item) throws Exception {
		if (!id.equals(item.getId()))
			throw new BadRequestException("No coinciden los ID");
		if (!dao.findById(item.getId()).isPresent())
			throw new NotFoundException("Missing item");
		return dao.save(item); // ConstraintViolationException
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@Operation(summary = "Eliminación de un contacto")
	@SecurityRequirement(name = "bearerAuth")
	public void delete(@PathVariable String id) throws Exception {
		try {
			dao.deleteById(id);
		} catch (Exception e) {
			throw new NotFoundException("Missing item", e);
		}
	}

}
