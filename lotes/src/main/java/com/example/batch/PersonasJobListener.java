package com.example.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.model.Persona;

@Component
public class PersonasJobListener implements JobExecutionListener {
	private static final Logger log = LoggerFactory.getLogger(PersonasJobListener.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("-------------------> Finalizado");
			jdbcTemplate.query("SELECT id, nombre, correo, ip FROM personas", 
					(rs, row) -> new Persona(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4)))
			.forEach(p -> log.info("Fila: " + p));
		}
	}	
}
