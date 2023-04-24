package com.demo.app.employeeDepartmentCRUDOps.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.app.employeeDepartmentCRUDOps.model.Department;
public interface DepartmentRepository extends JpaRepository<Department, Long> {
	
	

	  
}
