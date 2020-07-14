package com.extractor.springboot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.extractor.springboot.web")
public class DataExtractShowApp {

	public static void main(String[] args) {
		SpringApplication.run(DataExtractShowApp.class, args);
	}
}

