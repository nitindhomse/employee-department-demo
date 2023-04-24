package com.demo.app.employeeDepartmentCRUDOps.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.app.employeeDepartmentCRUDOps.model.Employee;
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
