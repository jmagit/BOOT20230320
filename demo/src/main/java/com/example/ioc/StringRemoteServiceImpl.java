package com.example.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.exceptions.InvalidDataException;

@Service
@Qualifier("Remoto")
public class StringRemoteServiceImpl implements StringService {
	@Autowired
	private StringRepository dao;
	
	@Override
	public String get(Integer id) {
		return dao.load() + " en remoto";
	}

	@Override
	public void add(String item) throws InvalidDataException {
		dao.save(item);
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
