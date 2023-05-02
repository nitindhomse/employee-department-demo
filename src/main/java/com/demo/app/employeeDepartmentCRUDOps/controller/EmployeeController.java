package com.demo.app.employeeDepartmentCRUDOps.controller;


import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.demo.app.employeeDepartmentCRUDOps.dto.AllDataResponseVO;
import com.demo.app.employeeDepartmentCRUDOps.dto.EmployeeDTO;
import com.demo.app.employeeDepartmentCRUDOps.dto.SearchCriteria;
import com.demo.app.employeeDepartmentCRUDOps.exception.EmployeeNotExistsException;
import com.demo.app.employeeDepartmentCRUDOps.constants.Constants;
import com.demo.app.employeeDepartmentCRUDOps.model.Employee;
import com.demo.app.employeeDepartmentCRUDOps.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	
	private final EmployeeService employeeService;
	private final RabbitTemplate rabbitTemplate;
	
	public EmployeeController(EmployeeService employeeService,  RabbitTemplate rabbitTemplate) {	
		this.employeeService = employeeService;
		this.rabbitTemplate = rabbitTemplate;	
	}
	
	@PostMapping("/")
	public Employee create( @Valid @RequestBody EmployeeDTO employee) throws JsonProcessingException {
		
		Optional<Employee> emp = employeeService.buildEmployee(employee, Constants.ACTION_ADD);					
		// Send Message to new-employee Messaging Queue
		rabbitTemplate.convertAndSend(Constants.TOPIC_EXCHANGE_NAME, "new.emp", employee);
			
		return employeeService.save(emp.get());
	}
	
	@PutMapping("/")
	public Employee update( @Valid @RequestBody EmployeeDTO employee) {
	
		Optional<Employee> emp = employeeService.buildEmployee(employee, Constants.ACTION_UPDATE);					
		// Send Message to new-employee Messaging Queue
		rabbitTemplate.convertAndSend(Constants.TOPIC_EXCHANGE_NAME, "new.emp", employee);
				
		return employeeService.save(emp.get());
	}
	
	
	@GetMapping("/{id}")
	public Optional<Employee> get(@PathVariable("id") Long id) {

		return employeeService.findById(id);					 
	}
	
	
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") Long id) {
		
		if(employeeService.existsById(id)) {
			employeeService.deleteById(id);
		}else {
			logger.error("Employee not exists to delete with id : {}", id);						
			throw new EmployeeNotExistsException("Employee with emp ID : " + id + "not Exists");
		}		
		return Constants.EMP_REMOVE_SUCCESS_MESSAGE;
		 
	}
	
	@GetMapping("/getAllEmployees")
	public AllDataResponseVO getAllEmployees(@RequestBody SearchCriteria criteria) {
		
		return employeeService.getAllEmployeesList(criteria);			 
	}
	
	@GetMapping("/search")
	public List<Employee> search(@RequestParam("query") String searchQuery) {

		return employeeService.findEmployeeByNameOrPhoneNumber(searchQuery);
	}
}
