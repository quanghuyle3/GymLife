package com.dbmanagement.GymLife;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.dbmanagement.GymLife.dao.AppDAO;

@SpringBootApplication
public class GymLifeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymLifeApplication.class, args);

	}

	// For quick testing
	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO) {
		return runner -> {

		};
	}

}
