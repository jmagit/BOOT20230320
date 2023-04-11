package com.example.application.resources;

import java.sql.Timestamp;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.application.services.CatalogoService;
import com.example.domains.entities.dtos.NovedadesDTO;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Value;

@RestController
@RequestMapping(path = "/")
public class CatalogoResource {
	@Autowired
	private CatalogoService srv;

	@Value
	public static class CatalogoResources {
		@Value
		public class CatalogoLinks {
			public class Href {
				private String href;
				public String getHref() { return href; }
				public Href(String path) {
					href = ServletUriComponentsBuilder.fromCurrentRequest().path(path).toUriString();
				}
			}
			private Href self = new Href("");
			private Href actores = new Href("/actores/v1");
			private Href peliculas = new Href("/peliculas/v1");
			private Href categorias = new Href("/categorias/v1");
			private Href idiomas = new Href("/idiomas/v1");
			private Href novedades = new Href("/novedades/v1");
			private Href documentacion = new Href("/open-api");
		}

		private CatalogoLinks _links = new CatalogoLinks();
	}

	@GetMapping(path = "/")
	public ResponseEntity<CatalogoResources> getResources() {
		return ResponseEntity.ok().header("Content-Type", "application/hal+json").body(new CatalogoResources());
	}
	
	@GetMapping(path = "/novedades/v1")
	public NovedadesDTO novedades(@Parameter(example = "2021-01-01 00:00:00") @RequestParam(required = false, defaultValue = "2021-01-01 00:00:00") Timestamp fecha) {
		// Timestamp fecha = Timestamp.valueOf("2019-01-01 00:00:00");
		if(fecha == null)
			fecha = Timestamp.from(Instant.now().minusSeconds(36000));
		return srv.novedades(fecha);
	}

}
