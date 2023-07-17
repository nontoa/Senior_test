package com.example.startupapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableFeignClients
@EnableJpaRepositories
@EnableRetry
public class StartupAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(StartupAppApplication.class, args);
	}

}
