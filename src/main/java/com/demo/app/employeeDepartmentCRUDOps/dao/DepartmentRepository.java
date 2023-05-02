package com.demo.app.employeeDepartmentCRUDOps.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.app.employeeDepartmentCRUDOps.model.Department;
public interface DepartmentRepository extends JpaRepository<Department, Long> {
	
	
    Optional<Department> findByDeptName(String name);
	
	boolean existsByDeptName(String name);
	  
}
