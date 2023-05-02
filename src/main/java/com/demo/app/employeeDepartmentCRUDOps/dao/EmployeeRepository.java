package com.demo.app.employeeDepartmentCRUDOps.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.app.employeeDepartmentCRUDOps.model.Employee;
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	boolean existsById(Long empId);
	
	@Query(value = "SELECT emp FROM Employee emp " + "WHERE " + "emp.empName LIKE CONCAT('%',:searchKey,'%') OR "
			+ "emp.phoneNumber LIKE CONCAT('%',:searchKey,'%')")
	List<Employee> findWhereNameOrPhoneNumberLike(@Param("searchKey") String searchKey);

}
