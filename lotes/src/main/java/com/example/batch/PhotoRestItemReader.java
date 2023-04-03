package com.example.batch;

import java.util.Iterator;

import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.PhotoDTO;
import com.example.proxies.PhotoProxy;

@Component
public class PhotoRestItemReader implements ItemReader<PhotoDTO>, ItemStream {
	private Iterator<PhotoDTO> cache;
	
	@Autowired
	private PhotoProxy srv;	
	
	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		cache = srv.getAll().iterator();
	}
	
	@Override
	public PhotoDTO read() {
		// Esta es la buena
		 return cache != null && cache.hasNext() ? cache.next() : null;
		// Ejemplo de fallos
//		if(cache == null || !cache.hasNext()) return null;
//		PhotoDTO siguiente = cache.next();
//		if(siguiente.getId().endsWith("0"))
//			 throw new UnexpectedJobExecutionException("Fallo forzado: " + siguiente.toString());
//		return siguiente;
	}
	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		// TODO Auto-generated method stub		
	}
	@Override
	public void close() throws ItemStreamException {
		cache = null;
	}
}
