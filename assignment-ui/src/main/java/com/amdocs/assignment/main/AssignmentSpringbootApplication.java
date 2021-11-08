package com.amdocs.assignment.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.amdocs")
public class AssignmentSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentSpringbootApplication.class, args);
	}
}
