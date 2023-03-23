package com.example.ioc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@Service
@Qualifier("Local")
public class StringServiceImpl implements StringService {
	private StringRepository dao;
	
	public StringServiceImpl(StringRepository dao) {
		this.dao = dao;
		System.out.println("Creo StringServiceImpl");
	}
	
	@Override
	public String get(Integer id) {
		return dao.load() + " en local";
	}

	@Override
	public void add(String item) throws NotFoundException {
		
		try {
			dao.save(item);
		} catch (InvalidDataException ex) {
			throw new NotFoundException("No encontrado", ex);
		}
	}

	@Override
	public void modify(String item) throws InvalidDataException {
		dao.save(item);
	}

	@Override
	public void remove(Integer id) throws InvalidDataException {
		dao.save(id.toString());
	}

}
