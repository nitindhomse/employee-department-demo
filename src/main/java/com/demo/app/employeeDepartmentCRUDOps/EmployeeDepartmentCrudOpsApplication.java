package com.demo.app.employeeDepartmentCRUDOps;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.demo.app.employeeDepartmentCRUDOps.constants.Constants;

@SpringBootApplication
public class EmployeeDepartmentCrudOpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeDepartmentCrudOpsApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	Queue queue() {
		return new Queue(Constants.NEW_EMPLOYEE_QUEUE, false);
		
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(Constants.TOPIC_EXCHANGE_NAME);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("*.emp.#");
	}
	

}
