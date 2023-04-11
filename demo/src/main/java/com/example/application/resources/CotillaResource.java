package com.example.application.resources;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.domains.entities.dtos.PelisDto;

//import com.example.application.proxies.CatalogoProxy;
//import com.example.application.proxies.PhotoProxy;
//import com.example.domains.entities.dtos.PelisDto;
//import com.example.domains.entities.dtos.PhotoDTO;
//import com.netflix.appinfo.InstanceInfo;
//import com.netflix.discovery.EurekaClient;
//
//import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//import io.micrometer.observation.annotation.Observed;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Ejemplos de conexiones
 * @author Javier
 *
 */
//@Observed
@RestController
@RequestMapping(path = "/api/cotilla")
public class CotillaResource {
//	@Autowired
//	RestTemplate srv;
//
//	@Autowired
//	@LoadBalanced
//	RestTemplate srvLB;

//	@Autowired
//	private EurekaClient discoveryEurekaClient;
//
//	@GetMapping(path = "/descubre/eureka/{nombre}")
//	public InstanceInfo serviceEurekaUrl(String nombre) {
//	    InstanceInfo instance = discoveryEurekaClient.getNextServerFromEureka(nombre, false);
//	    return instance; //.getHomePageUrl();
//	}
//	
//	@Autowired
//	private DiscoveryClient discoveryClient;
//
//	@GetMapping(path = "/descubre/cloud/{nombre}")
//	public List<ServiceInstance> serviceUrl(String nombre) {
//	    return discoveryClient.getInstances(nombre);
//	}
	
//	@GetMapping(path = "/balancea/rt")
//	public List<String> getBalanceoRT() {
//		List<String> rslt = new ArrayList<>();
//		LocalDateTime inicio = LocalDateTime.now();
//		rslt.add("Inicio: " + inicio);
//		for(int i = 0; i < 11; i++)
//			try {
//				LocalTime ini = LocalTime.now();
//				rslt.add(srvLB.getForObject("lb://CATALOGO-SERVICE/actuator/info", String.class)
//						+ " (" + ini.until(LocalTime.now(), ChronoUnit.MILLIS) + " ms)" );
//			} catch (Exception e) {
//				rslt.add(e.getMessage());
//			}
//		LocalDateTime fin = LocalDateTime.now();
//		rslt.add("Final: " + fin + " (" + inicio.until(fin, ChronoUnit.MILLIS) + " ms)");		
//		return rslt;
//	}
//	@GetMapping(path = "/pelis/rt")
//	public List<PelisDto> getPelisRT() {
////		ResponseEntity<List<PelisDto>> response = srv.exchange(
////				"lb://CATALOGO-SERVICE/v1/peliculas?mode=short", 
//		ResponseEntity<List<PelisDto>> response = srvLB.exchange(
//				"http://localhost:8010/v1/peliculas?mode=short", 
//				HttpMethod.GET,
//				HttpEntity.EMPTY, 
//				new ParameterizedTypeReference<List<PelisDto>>() {}
//		);
//		return response.getBody();
//	}
//	@GetMapping(path = "/pelis/{id}/rt")
//	public PelisDto getPelisRT(@PathVariable int id) {
//		return srvLB.getForObject("lb://CATALOGO-SERVICE/v1/peliculas/{key}?mode=short", PelisDto.class, id);
////		return srv.getForObject("http://localhost:8010/v1/peliculas/{key}?mode=short", PelisDto.class, id);
//	}
//	
//	@Autowired
//	CatalogoProxy proxy;
//
//	@GetMapping(path = "/balancea/proxy")
//	public List<String> getBalanceoProxy() {
//		List<String> rslt = new ArrayList<>();
//		for(int i = 0; i < 11; i++)
//			try {
//				rslt.add(proxy.getInfo());
//			} catch (Exception e) {
//				rslt.add(e.getMessage());
//			}
//		return rslt;
//	}
//	@GetMapping(path = "/pelis/proxy")
//	public List<PelisDto> getPelisProxy() {
//		return proxy.getPelis();
//	}
//
//	@GetMapping(path = "/pelis/{id}/proxy")
//	public PelisDto getPelisProxy(@PathVariable int id) {
//		return proxy.getPeli(id);
//	}
	
//	@PostMapping(path = "/pelis/{id}/like")
//	public String getPelisLike(@PathVariable int id, @Parameter(hidden = true) @RequestHeader(required = false) String authorization) {
//		System.out.println("Entrada");
//		if(authorization == null)
//			return proxy.meGusta(id);
//		return proxy.meGusta(id, authorization);
//	}

//	@Autowired
//	private CircuitBreakerFactory cbFactory;
//	
//	@GetMapping(path = "/circuit-breaker/factory")
//	public List<String> getCircuitBreakerFactory() {
//		List<String> rslt = new ArrayList<>();
//		LocalDateTime inicio = LocalDateTime.now();
//		rslt.add("Inicio: " + inicio);
//		for(int i = 0; i < 100; i++) {
//				LocalTime ini = LocalTime.now();
//				rslt.add(cbFactory.create("slow").run(
//						() -> srvLB.getForObject("lb://CATALOGO-SERVICE/actuator/info", String.class), 
//						throwable -> "fallback")
//						+ " (" + ini.until(LocalTime.now(), ChronoUnit.MILLIS) + " .ms)" );
//			}
//		LocalDateTime fin = LocalDateTime.now();
//		rslt.add("Final: " + fin + " (" + inicio.until(fin, ChronoUnit.MILLIS) + " ms)");		
//		return rslt;
//	}
//	
//	@GetMapping(path = "/circuit-breaker/anota")
//	public List<String> getCircuitBreakerAnota() {
//		List<String> rslt = new ArrayList<>();
//		LocalDateTime inicio = LocalDateTime.now();
//		rslt.add("Inicio: " + inicio);
//		for(int i = 0; i < 100; i++)
//			rslt.add(getInfo(LocalTime.now()));
//		LocalDateTime fin = LocalDateTime.now();
//		rslt.add("Final: " + fin + " (" + inicio.until(fin, ChronoUnit.MILLIS) + " ms)");		
//		return rslt;
//	}
//	
//	@CircuitBreaker(name = "default", fallbackMethod = "fallback")
//	private String getInfo(LocalTime ini) {
//		return srv.getForObject("http://localhost:8010/actuator/info", String.class)
//						+ " (" + ini.until(LocalTime.now(), ChronoUnit.MILLIS) + " ms)";
////		return srvLB.getForObject("lb://CATALOGO-SERVICE/actuator/info", String.class)
////				+ " (" + ini.until(LocalTime.now(), ChronoUnit.MILLIS) + " ms)";
//	}
//	private String fallback(LocalTime ini, CallNotPermittedException e) {
//		return "CircuitBreaker is open";
//	}
//
//	private String fallback(LocalTime ini, Exception e) {
//		return "Fallback: " + e.getMessage()
//						+ " (" + ini.until(LocalTime.now(), ChronoUnit.MILLIS) + " ms)";
//	}
	
//	@Value("${particular.para.demos}")
//	String config;
//	
//	@GetMapping(path = "/config")
//	public String getConfig() {
//		return config;
//	}

//	@Autowired
//	PhotoProxy proxyExterno;
//	@GetMapping(path = "/fotos")
//	public List<PhotoDTO> getFotosProxy() {
//		return proxyExterno.getAll();
//	}

	
	
}
