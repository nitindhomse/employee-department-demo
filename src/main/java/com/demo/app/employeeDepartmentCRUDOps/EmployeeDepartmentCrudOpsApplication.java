package com.demo.app.employeeDepartmentCRUDOps;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmployeeDepartmentCrudOpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeDepartmentCrudOpsApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
