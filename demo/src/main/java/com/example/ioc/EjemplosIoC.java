package com.example.ioc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


import lombok.AllArgsConstructor;
import lombok.Data;

@Configuration
public class EjemplosIoC {
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

	public void run() {
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
