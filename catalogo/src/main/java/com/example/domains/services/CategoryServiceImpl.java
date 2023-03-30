package com.example.domains.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.example.domains.contracts.repositories.CategoryRepository;
import com.example.domains.contracts.services.CategoryService;
import com.example.domains.entities.Category;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository dao;
	
	@Override
	public List<Category> getAll() {
		return dao.findAll(Sort.by("name"));
	}

	@Override
	public Optional<Category> getOne(Integer id) {
		return dao.findById(id);
	}

	@Override
	public Category add(Category item) throws DuplicateKeyException, InvalidDataException {
		if(item == null)
			throw new InvalidDataException("Faltan los datos");
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		if(getOne(item.getCategoryId()).isPresent())
			throw new DuplicateKeyException();
		return dao.save(item);
	}

	@Override
	public Category modify(Category item) throws NotFoundException, InvalidDataException {
		if(item == null)
			throw new InvalidDataException("Faltan los datos");
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		if(getOne(item.getCategoryId()).isEmpty())
			throw new NotFoundException();
		return dao.save(item);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public void delete(Category item) throws InvalidDataException {
		if(item == null)
			throw new InvalidDataException("Faltan los datos");
		deleteById(item.getCategoryId());
	}

	@Override
	public List<Category> novedades(Timestamp fecha) {
		return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);
	}

}
