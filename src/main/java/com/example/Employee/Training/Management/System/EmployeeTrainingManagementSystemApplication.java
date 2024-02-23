package com.example.Employee.Training.Management.System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(value = "com.example.Employee.Training.Management.System.Repository")
@EntityScan("com.example.Employee.Training.Management.System.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class EmployeeTrainingManagementSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmployeeTrainingManagementSystemApplication.class, args);
	}
}

