package com.example.application.services;

import java.sql.Timestamp;

import com.example.domains.entities.dtos.NovedadesDTO;

public interface CatalogoService {

	NovedadesDTO novedades(Timestamp fecha);

}