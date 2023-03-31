package com.example.domains.contracts.repositories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.domains.entities.Actor;

@DataJpaTest
class ActorRepositoryMemoryTest {
	@Autowired	
	private TestEntityManager em;
	@BeforeEach
	void setUp() throws Exception {
		em.persist(new Actor(0, "Pepito", "Grillo"));
		em.persist(new Actor(0, "Carmelo", "Coton"));
		em.persist(new Actor(0, "Capitan", "Tan"));
	}
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
