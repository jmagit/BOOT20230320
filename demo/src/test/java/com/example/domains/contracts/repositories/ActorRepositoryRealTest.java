package com.example.domains.contracts.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest()
@ActiveProfiles("test")
class ActorRepositoryRealTest {
	@Autowired
	ActorRepository dao;

	@Test
	void testFindAll() {
		assertThat(dao.findAll().size()).isGreaterThanOrEqualTo(200);
	}
//
//	@Test
//	void testFindTop5ByFirstNameStartingWithOrderByLastNameDesc() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindTop5ByFirstNameStartingWith() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindByActorIdNotNull() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindConJPQLInt() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindConJPQL() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindConSQL() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetById() {
//		fail("Not yet implemented");
//	}

}
