package com.example.domains.entities.dtos;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface ActorShort {
	@JsonProperty("id")
	int getActorId();
	@Value("#{target.firstName + ' ' + target.lastName}")
	String getNombre();
}
