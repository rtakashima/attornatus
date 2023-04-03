package com.attornatus.demo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AttornatusApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttornatusApplication.class, args);
	}

  @Bean
  ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
