package com.example.domains.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;

@DataJpaTest
@ComponentScan(basePackages = "com.example")
class ActorServiceImplTest {

	@MockBean
	ActorRepository dao;
	
	@Autowired
	ActorService srv;
	
	@Test
	void testGetAll_isNotEmpty() {
		List<Actor> lista = new ArrayList<>(
		        Arrays.asList(new Actor(1, "Pepito", "GRILLO"),
		        		new Actor(2, "Carmelo", "COTON"),
		        		new Actor(3, "Capitan", "TAN")));

		when(dao.findAll()).thenReturn(lista);
		var rslt = srv.getAll();
		assertThat(rslt.size()).isEqualTo(3);
		verify(dao, times(1)).findAll();
		}
	@Test
	void testGetOne_valid() {
		List<Actor> lista = new ArrayList<>(
		        Arrays.asList(new Actor(1, "Pepito", "GRILLO"),
		        		new Actor(2, "Carmelo", "COTON"),
		        		new Actor(3, "Capitan", "TAN")));

		when(dao.findById(1)).thenReturn(Optional.of(new Actor(1, "Pepito", "GRILLO")));
		var rslt = srv.getOne(1);
		assertThat(rslt.isPresent()).isTrue();
		
	}
	@Test
	void testGetOne_notfound() {
		when(dao.findById(1)).thenReturn(Optional.empty());
		var rslt = srv.getOne(1);
		assertThat(rslt.isEmpty()).isTrue();
		
	}

//	@Test
//	void testGetByProjectionClassOfT() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetByProjectionSortClassOfT() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetByProjectionPageableClassOfT() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetAllSort() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetAllPageable() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetAll() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetOne() {
//		fail("Not yet implemented");
//	}

	@Test
	void testAdd() throws DuplicateKeyException, InvalidDataException {
		when(dao.save(any(Actor.class))).thenReturn(null, null);
		assertThrows(InvalidDataException.class, () -> srv.add(null));
		verify(dao, times(0)).save(null);
	}
//
//	@Test
//	void testModify() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDelete() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDeleteById() {
//		fail("Not yet implemented");
//	}

}
