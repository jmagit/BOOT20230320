package com.example.domains.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.FilmActor;
import com.example.domains.entities.FilmCategory;
import com.example.domains.entities.Language;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.transaction.Transactional;
import lombok.NonNull;

@Service
public class FilmServiceImpl implements FilmService {
	@Autowired
	FilmRepository dao;

	@Override
	public <T> List<T> getByProjection(Class<T> type) {
		return dao.findAllBy(type);
	}

	@Override
	public <T> Iterable<T> getByProjection(Sort sort, Class<T> type) {
		return dao.findAllBy(sort, type);
	}

	@Override
	public <T> Page<T> getByProjection(Pageable pageable, Class<T> type) {
		return dao.findAllBy(pageable, type);
	}

	@Override
	public Iterable<Film> getAll(Sort sort) {
		return dao.findAll(sort);
	}

	@Override
	public Page<Film> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Film> getAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Film> getOne(Integer id) {
		return dao.findById(id);
	}

	@Override
	@Transactional
	public Film add(Film item) throws DuplicateKeyException, InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage());
		if(dao.existsById(item.getFilmId()))
			throw new DuplicateKeyException(item.getErrorsMessage());
		var actores = item.getActors();
		var categorias = item.getCategories();
		item.clearActors();
		item.clearCategories();
		var newItem = dao.save(item);
		actores.forEach(ele -> newItem.addActor(ele));
		categorias.forEach(ele -> newItem.addCategory(ele));
		return dao.save(newItem);
	}

	@Override
	@Transactional
	public Film modify(Film item) throws NotFoundException, InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage());
		var leido = dao.findById(item.getFilmId());
		if(leido.isEmpty())
			throw new NotFoundException();
		var target = leido.get();
		target.setDescription(item.getDescription());
		target.setLength(item.getLength());
		target.setRating(item.getRating());
		target.setReleaseYear(item.getReleaseYear());
		target.setRentalDuration(item.getRentalDuration());
		target.setRentalRate(item.getRentalRate());
		target.setReplacementCost(item.getReplacementCost());
		target.setTitle(item.getTitle());
		target.setLanguage(item.getLanguage());
		target.setLanguageVO(item.getLanguageVO());
		// Borra los actores que sobran
		target.getActors().stream()
			.filter(old -> !item.getActors().contains(old))
			.forEach(old -> target.removeActor(old));
		// Añade los actores que faltan
		item.getActors().stream()
			.filter(nue -> !target.getActors().contains(nue))
			.forEach(nue -> target.addActor(nue));
		// Añade los categorias que faltan
		target.getCategories().stream()
			.filter(old -> !item.getCategories().contains(old))
			.forEach(old -> target.removeCategory(old));
		// Borra los categorias que sobran
		item.getCategories().stream()
			.filter(nue -> !target.getCategories().contains(nue))
			.forEach(nue -> target.addCategory(nue));
		
		return dao.save(target);
	}

	@Override
	public void delete(Film item) throws InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		deleteById(item.getFilmId());
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public List<Film> novedades(@NonNull Timestamp fecha) {
		return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);
	}

}
