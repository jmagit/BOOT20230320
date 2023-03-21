package com.example.ioc;

import org.springframework.stereotype.Service;

@Service
public class StringServiceImpl implements StringService {
	private StringRepository dao;
	
	public StringServiceImpl(StringRepository dao) {
		this.dao = dao;
	}
	
	@Override
	public String get(Integer id) {
		return dao.load();
	}

	@Override
	public void add(String item) {
		dao.save(item);
	}

	@Override
	public void modify(String item) {
		dao.save(item);
	}

	@Override
	public void remove(Integer id) {
		dao.save(id.toString());
	}

}
