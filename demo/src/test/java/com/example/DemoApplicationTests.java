package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.example.ioc.StringRepository;
import com.example.ioc.StringRepositoryMockImpl;

@SpringBootTest(properties = {"mi.valor=Falso"})
//@Disabled
class DemoApplicationTests {

	@Autowired
	StringRepository dao;
	
	@Test
	void contextLoads() {
		assertEquals("Soy el StringRepositoryImpl", dao.load());
	}
	
	@Value("${mi.valor:(Sin valor)}")
	private String config;
	@Test
	void valueLoads() {
		assertEquals("Valor por defecto", config);
	}

	public static class IoCTestConfig {
		@Bean
		StringRepository getServicio() {
			// return new ServicioImpl();
			return new StringRepositoryMockImpl();
		}
	}
	
	@Nested
	@ContextConfiguration(classes = IoCTestConfig.class)
	@ActiveProfiles("test")
	class IoCTest {
		@Autowired
		StringRepository dao;
		
		@Test
		void contextLoads() {
			assertEquals("Soy el doble de pruebas de StringRepository", dao.load());
		}
		
		@Value("${mi.valor:(Sin valor)}")
		private String config;
		@Test
		void valueLoads() {
			assertEquals("Valor para las pruebas", config);
		}
	}
	
	@Nested
	class IoCUnicoTest {
		@TestConfiguration
		public static class IoCParticularTestConfig {
			@Bean
			StringRepository getServicio() {
				// return new ServicioImpl();
				return new StringRepositoryMockImpl();
			}
		}

		@Autowired
		StringRepository dao;
		
		@Test
		void contextLoads() {
			assertEquals("Soy el doble de pruebas de StringRepository", dao.load());
		}
	}
	
}
