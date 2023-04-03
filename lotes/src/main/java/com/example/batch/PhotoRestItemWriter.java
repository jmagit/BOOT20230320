package com.example.batch;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.model.PhotoDTO;
import com.example.proxies.PhotoProxy;

public class PhotoRestItemWriter implements ItemWriter<PhotoDTO> {
	@Autowired
	private PhotoProxy srv;	

	@Override
	public void write(Chunk<? extends PhotoDTO> items) throws Exception {
		items.forEach(f -> srv.send(f));
	}

}
