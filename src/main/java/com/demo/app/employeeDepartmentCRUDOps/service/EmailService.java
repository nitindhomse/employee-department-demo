package com.demo.app.employeeDepartmentCRUDOps.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.demo.app.employeeDepartmentCRUDOps.dto.EmployeeDTO;


@Service
public class EmailService {
	
	@RabbitListener(queues="new-employee")
	public void sendEmail(EmployeeDTO emp) {
		System.out.println("==========Message received from New Employee Queue=====");
		
		System.out.println(emp.toString());
		
	}
		
}
