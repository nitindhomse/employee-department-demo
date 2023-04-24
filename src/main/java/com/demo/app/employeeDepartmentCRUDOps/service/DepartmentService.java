package com.demo.app.employeeDepartmentCRUDOps.service;

import org.springframework.stereotype.Service;

import com.demo.app.employeeDepartmentCRUDOps.dao.DepartmentRepository;
import com.demo.app.employeeDepartmentCRUDOps.model.Department;

@Service
public interface DepartmentService extends DepartmentRepository{

	Department findByDeptName(String name);
	
	boolean existsByDeptName(String name);
}
