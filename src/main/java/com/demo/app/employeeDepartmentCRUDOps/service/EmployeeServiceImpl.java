package com.demo.app.employeeDepartmentCRUDOps.service;

import java.util.Optional;

import com.demo.app.employeeDepartmentCRUDOps.dto.AllDataResponseVO;
import com.demo.app.employeeDepartmentCRUDOps.dto.EmployeeDTO;
import com.demo.app.employeeDepartmentCRUDOps.dto.SearchCriteria;
import com.demo.app.employeeDepartmentCRUDOps.model.Employee;

public interface EmployeeServiceImpl {

	public Optional<Employee> buildEmployee(EmployeeDTO emp, String action);
	
	public AllDataResponseVO getAllEmployeesList(SearchCriteria criteria);
}
