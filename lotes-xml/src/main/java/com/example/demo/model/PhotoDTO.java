package com.example.demo.model;

import lombok.Data;

@Data
public class PhotoDTO {
	private String id, author, url, download_url;
	private int width, height;
}
