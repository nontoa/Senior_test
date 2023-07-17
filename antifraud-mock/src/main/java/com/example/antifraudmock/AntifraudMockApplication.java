package com.example.antifraudmock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {WiremockServiceConfiguration.class})
public class AntifraudMockApplication {

	public static void main(String[] args) {
		SpringApplication.run(AntifraudMockApplication.class, args);
	}

}
