package com.soa.soaproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.soa.soaproject.model")
public class SoaprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoaprojectApplication.class, args);
	}

}
