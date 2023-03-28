package com.example;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.example.ioc.Rango;
import com.example.ioc.StringRepository;
import com.example.ioc.StringRepositoryImpl;
import com.example.ioc.StringRepositoryMockImpl;
import com.example.ioc.StringService;
import com.example.ioc.StringServiceImpl;
import com.example.ioc.UnaTonteria;

import lombok.AllArgsConstructor;
import lombok.Data;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	@Qualifier("Remoto")
	private StringService srv;
	
	@Autowired
	@Qualifier("Local")
	@Lazy
	private StringService srvLocal;
	
	@Value("${mi.valor:(Sin valor)}")
	private String config;
	
	@Autowired
	Rango rango;
	
	@Autowired(required = false)
	UnaTonteria tonteria;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Data @AllArgsConstructor
	class Actor {
		private int id;
		private String first_name, last_name;
	}
	class ActorRowMapper implements RowMapper<Actor> {
	      @Override
	      public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
	            return new Actor(rs.getInt("actor_id"), rs.getString("last_name"), rs.getString("first_name"));
	      }
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Aplicación arrancada");
		
//		StringRepository dao = new StringRepositoryImpl();
//		dao = new StringRepositoryMockImpl();
//		var srv = new StringServiceImpl(dao);
//		System.out.println(srv.get(1));
//		System.out.println(srvLocal.get(1));
//		srv.add("Este es el remoto");
//		srvLocal.add("Este es el local");
//		srv.add("Este es el remoto");
//		srvLocal.add("Este es el local");
//		System.out.println(rango.toString());
//		System.out.println(rango.getMin() + rango.getMax());
//		System.out.println(tonteria != null ? tonteria.dimeAlgo() : "Tonteria nula");
//		System.out.println(config);
//		srv.add("algo");
		var lst = jdbcTemplate.query("""
				SELECT actor_id, first_name, last_name
				from actor
				""", new ActorRowMapper()
				);
		//lst.forEach(System.out::println);
		jdbcTemplate.queryForList("""
				SELECT concat(first_name, ' ', last_name)
				from actor
				""", String.class).forEach(System.out::println);

	}

}
