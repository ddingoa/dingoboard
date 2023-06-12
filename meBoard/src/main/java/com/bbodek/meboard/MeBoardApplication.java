package com.bbodek.meboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MeBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeBoardApplication.class, args);
	}

}
