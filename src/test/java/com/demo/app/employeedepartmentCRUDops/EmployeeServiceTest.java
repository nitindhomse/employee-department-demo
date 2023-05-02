package com.demo.app.employeedepartmentCRUDops;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.demo.app.employeeDepartmentCRUDOps.controller.EmployeeController;
import com.demo.app.employeeDepartmentCRUDOps.dao.EmployeeRepository;
import com.demo.app.employeeDepartmentCRUDOps.dto.SearchCriteria;
import com.demo.app.employeeDepartmentCRUDOps.model.Department;
import com.demo.app.employeeDepartmentCRUDOps.model.Employee;
import com.demo.app.employeeDepartmentCRUDOps.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

	private final Logger logger = LoggerFactory.getLogger(EmployeeServiceTest.class);

	@InjectMocks
	private EmployeeService employeeService;

	@Mock
	private EmployeeRepository employeeRepository;

	private Employee emp;

	@BeforeEach
	private void createEmployeeObjects() throws JsonMappingException, JsonProcessingException {
		ObjectMapper empObjMapper = new ObjectMapper();
		String empJsonStr = "{\"empName\":\"Nitin\",\"city\":\"Pune\",\"phoneNumber\":\"+5544433\",\"gender\":\"Male\"}";
		emp = empObjMapper.readValue(empJsonStr, Employee.class);
		Set<Department> deptSet = new HashSet<Department>();
		Department dept = new Department();
		dept.setDeptId(1L);
		dept.setDeptName("Finance");
		deptSet.add(dept);
		emp.setDepartments(deptSet);

	}

	@Test
	public void findByEmpIdTest() {
		
		Long empId  = 1L;
		given(employeeRepository.findById(empId)).willReturn(Optional.of(emp));

		Employee savedEmployee = employeeService.findById(empId).get();

		assertThat(savedEmployee).isNotNull();

	}
	
	@Test
    public void saveEmployeeTest(){
        
        given(employeeRepository.save(emp)).willReturn(emp);

        Employee savedEmployee = employeeService.save(emp);

        assertThat(savedEmployee).isNotNull();
    }
	
	@Test
    public void deleteEmployeeTest(){
        long employeeId = 1L;
        willDoNothing().given(employeeRepository).deleteById(employeeId);
        employeeService.deleteById(employeeId);
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

}
