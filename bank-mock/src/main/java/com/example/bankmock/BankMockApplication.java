package com.example.bankmock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {WiremockServiceConfiguration.class})
public class BankMockApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankMockApplication.class, args);
	}

}
