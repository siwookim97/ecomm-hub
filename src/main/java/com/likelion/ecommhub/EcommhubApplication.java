package com.likelion.ecommhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EcommhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommhubApplication.class, args);
	}

}
