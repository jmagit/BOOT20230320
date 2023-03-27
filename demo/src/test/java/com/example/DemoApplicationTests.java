package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.example.ioc.StringRepository;
import com.example.ioc.StringRepositoryMockImpl;
import com.example.ioc.StringService;
import com.example.ioc.StringServiceImpl;

@SpringBootTest(properties = {"mi.valor=Falso"})
//@Disabled
class DemoApplicationTests {

	@Autowired
	StringRepository dao;
	
	@Autowired
	@Qualifier("Local")
	StringService srv;
	
	@Test
	void contextLoads() {
		assertEquals("Soy el StringRepositoryImpl", dao.load());
		assertEquals("Soy el StringRepositoryImpl en local", srv.get(1));
	}
	
	@Value("${mi.valor:(Sin valor)}")
	private String config;
	@Test
	void valueLoads() {
		assertEquals("Falso", config);
//		assertEquals("Valor por defecto", config);
	}

	public static class IoCTestConfig {
		@Bean
		StringRepository getRepository() {
			// return new ServicioImpl();
			return new StringRepositoryMockImpl();
		}
		@Bean
		StringService getServicio(StringRepository dao) {
			return new StringServiceImpl(dao);
		}
	}
	
	@Nested
	@ContextConfiguration(classes = IoCTestConfig.class)
	@ActiveProfiles("test")
	class IoCTest {
		@Autowired
		StringRepository dao;
		@Autowired
		@Qualifier("Local")
		StringService srv;
		
		@Test
		void contextLoads() {
//			assertEquals("Soy el doble de pruebas de StringRepository", dao.load());
			assertEquals("Soy el doble de pruebas de StringRepository en local", srv.get(1));
		}
		
		@Value("${mi.valor:(Sin valor)}")
		private String config;
		@Test
		void valueLoads() {
			assertEquals("Falso", config);
			//assertEquals("Valor para las pruebas", config);
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
			assertEquals("Soy el StringRepositoryImpl", dao.load());
		}
	}
	
}
