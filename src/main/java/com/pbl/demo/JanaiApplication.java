package com.pbl.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.pbl.demo.model")
public class JanaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JanaiApplication.class, args);
	}

}
