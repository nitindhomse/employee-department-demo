package com.demo.app.employeeDepartmentCRUDOps.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.app.employeeDepartmentCRUDOps.dto.AllDataResponseVO;
import com.demo.app.employeeDepartmentCRUDOps.dto.EmployeeDTO;
import com.demo.app.employeeDepartmentCRUDOps.dto.ResponseVO;
import com.demo.app.employeeDepartmentCRUDOps.dto.SearchCriteria;
import com.demo.app.employeeDepartmentCRUDOps.exception.InvalidDepartmentException;
import com.demo.app.employeeDepartmentCRUDOps.constants.Constants;
import com.demo.app.employeeDepartmentCRUDOps.model.Department;
import com.demo.app.employeeDepartmentCRUDOps.model.Employee;
import com.demo.app.employeeDepartmentCRUDOps.service.DepartmentService;
import com.demo.app.employeeDepartmentCRUDOps.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	
	@PostMapping("/add")
	public ResponseVO addNewEmployee(@RequestBody EmployeeDTO employee) throws JsonProcessingException {

		Employee emp = modelMapper.map(employee, Employee.class);
		Set<Department> deptSet = new HashSet<>();
		
		if((employee.getEmpName() != null && !employee.getEmpName().isEmpty())
				&& (employee.getGender() != null && !employee.getGender().isEmpty())) {
			
			if(!CollectionUtils.isEmpty(employee.getDepartmentList()))
				employee.getDepartmentList().forEach( deptName -> {
					Department dept = departmentService.findByDeptName(deptName);
					
					logger.info("Department found ==>  "+dept);
					
					if(dept != null) {
						deptSet.add(dept);
					}else {
						logger.error("Invalid Department : "+deptName);
							
						throw new InvalidDepartmentException(deptName + Constants.DEPT_NOT_EXIST);
					}
					
				});
		}else {
			return new ResponseVO(Constants.STATUS_OK, Constants.STATUS_FAILED, Constants.EMP_NAME_GENDER_EMPTY, null);
		}
			
		emp.setDepartments(deptSet);
		logger.info("EMP OBJECT===> "+emp.toString());	
		Employee savedEmp = employeeService.save(emp);
				
		rabbitTemplate.convertAndSend(Constants.TOPIC_EXCHANGE_NAME, "new.emp", 
				employee);
		
		
		return new ResponseVO(Constants.STATUS_OK, Constants.STATUS_SUCCESS, Constants.EMP_ADD_SUCCESS_MESSAGE, savedEmp);
			 
	}
	
	@PostMapping("/update")
	public ResponseVO updateEmployee(@RequestBody EmployeeDTO employee) {

		if((employee.getEmpName() != null && !employee.getEmpName().isEmpty())
				&& (employee.getGender() != null && !employee.getGender().isEmpty())) {
			Optional<Employee> emp = null;
			if(employee.getEmpId() != null ) 
				emp = employeeService.findById(employee.getEmpId());
			
			if(emp.isPresent()) {
				Employee mappedEmp = modelMapper.map(employee, Employee.class);
				Set<Department> deptSet = new HashSet<>();
				
				if(!CollectionUtils.isEmpty(employee.getDepartmentList()))
				employee.getDepartmentList().forEach( deptName -> {
					Department dept = departmentService.findByDeptName(deptName);
					
					logger.info("Department found ==>  "+dept);
					
					if(dept != null) {
						deptSet.add(dept);
					}else {
						logger.error("Invalid Department : "+deptName);
						throw new InvalidDepartmentException(deptName + Constants.DEPT_NOT_EXIST);
					}
					
				});
				
				mappedEmp.setDepartments(deptSet);
				
				logger.info("EMP OBJECT===> "+emp.toString());
				
				
				Employee updatedEmp = employeeService.save(mappedEmp);
				return new ResponseVO(Constants.STATUS_OK, Constants.STATUS_SUCCESS, Constants.EMP_UPDATE_SUCCESS_MESSAGE, updatedEmp);				 

			}else {
				logger.error("Employee Id is Null or Invalid : "+employee.getEmpId());
			}
		}else {
			return new ResponseVO(Constants.STATUS_OK, Constants.STATUS_FAILED, Constants.EMP_NAME_GENDER_EMPTY, null);
		}
				
		return new ResponseVO(Constants.STATUS_INERNAL_SERVER_ERROR, Constants.STATUS_FAILED, Constants.STATUS_FAILED, null);	
	}
	
	
	@GetMapping("/getEmployee/{id}")
	public ResponseVO getEmployee(@PathVariable("id") Long id) {

		ResponseVO responseVO = null;
		Optional<Employee> emp = employeeService.findById(id);
		if(emp.isPresent()) {
			responseVO = new ResponseVO(Constants.STATUS_OK, Constants.STATUS_SUCCESS, Constants.STATUS_SUCCESS, emp);
		}else {
			responseVO = new ResponseVO(Constants.STATUS_NOT_FOUND, Constants.STATUS_FAILED, Constants.RECORD_DOES_NOT_FOUND, emp);
		}

		return responseVO;
				 
	}
	
	@GetMapping("/getAllEmployees")
	public AllDataResponseVO getAllEmployees(@RequestBody SearchCriteria criteria) {

		AllDataResponseVO responseVO = new AllDataResponseVO();
		Sort sort = criteria.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name())?
	                Sort.by(criteria.getSortBy()).ascending(): Sort.by(criteria.getSortBy()).descending();
		Pageable pageable = PageRequest.of(criteria.getOffset(),criteria.getMax(), sort);
		
		Page<Employee> empPage= employeeService.findAll(pageable);
		
		responseVO.setStatusCode(Constants.STATUS_OK);
		responseVO.setMessage(Constants.STATUS_SUCCESS);
		responseVO.setTotalRecords(empPage.getTotalElements());
		responseVO.setPageNumber(empPage.getNumber() + 1);
		responseVO.setPageSize(empPage.getSize());
		responseVO.setData(empPage.getContent());
		
		return  responseVO;
				 
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseVO deleteEmployee(@PathVariable("id") Long id) {
		ResponseVO responseVO= null;
		if(employeeService.existsById(id)) {
			employeeService.deleteById(id);
			responseVO =  new ResponseVO(Constants.STATUS_OK, Constants.STATUS_SUCCESS, Constants.EMP_REMOVE_SUCCESS_MESSAGE, null);				 

		}else {
			responseVO =  new ResponseVO(Constants.STATUS_NOT_FOUND, Constants.STATUS_FAILED, Constants.RECORD_DOES_NOT_FOUND, null);				 

		}
		
		return responseVO;
		 
	}
}
