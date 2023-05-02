package com.demo.app.employeeDepartmentCRUDOps.service;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.demo.app.employeeDepartmentCRUDOps.constants.Constants;
import com.demo.app.employeeDepartmentCRUDOps.dao.DepartmentRepository;
import com.demo.app.employeeDepartmentCRUDOps.dao.EmployeeRepository;
import com.demo.app.employeeDepartmentCRUDOps.dto.AllDataResponseVO;
import com.demo.app.employeeDepartmentCRUDOps.dto.EmployeeDTO;
import com.demo.app.employeeDepartmentCRUDOps.dto.SearchCriteria;
import com.demo.app.employeeDepartmentCRUDOps.exception.EmployeeNotExistsException;
import com.demo.app.employeeDepartmentCRUDOps.exception.InvalidDepartmentException;
import com.demo.app.employeeDepartmentCRUDOps.model.Department;
import com.demo.app.employeeDepartmentCRUDOps.model.Employee;

@Transactional
@Service
public class EmployeeService {
	
	private final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	private final DepartmentRepository departmentRepository;
	private final ModelMapper modelMapper;
	private final EmployeeRepository employeeRepository;

	public EmployeeService(DepartmentRepository departmentRepository, ModelMapper modelMapper,
			EmployeeRepository employeeRepository) {
		this.departmentRepository = departmentRepository;
		this.modelMapper = modelMapper;
		this.employeeRepository = employeeRepository;
	}

	public Optional<Employee> buildEmployee(EmployeeDTO employee, String action) {

		boolean isExists = false;
		Employee emp = modelMapper.map(employee, Employee.class);
		Set<Department> deptSet = new HashSet<>();

		if (action.equals(Constants.ACTION_UPDATE)) {
			isExists = employeeRepository.existsById(employee.getEmpId());
		}
		if (action.equals(Constants.ACTION_ADD) || isExists) {

			if (!CollectionUtils.isEmpty(employee.getDepartmentList()))
				employee.getDepartmentList().forEach(deptName -> {
					Optional<Department> dept = departmentRepository.findByDeptName(deptName);
					logger.info("Department found : {}", deptName);
					if (dept.isPresent()) {
						deptSet.add(dept.get());
					} else {
						logger.error("Invalid Department : {}", deptName);
						throw new InvalidDepartmentException(deptName + Constants.DEPT_NOT_EXIST);
					}

				});
			emp.setDepartments(deptSet);

		} else {
			logger.error("Employee not exists : {}", employee.getEmpId());
			throw new EmployeeNotExistsException("Employee with emp ID : " + employee.getEmpId() + "Not Exists");
		}

		return Optional.of(emp);
	}

	
	public AllDataResponseVO getAllEmployeesList(SearchCriteria criteria) {

		AllDataResponseVO responseVO = new AllDataResponseVO();
		Sort sort = criteria.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name())
				? Sort.by(criteria.getSortBy()).ascending()
				: Sort.by(criteria.getSortBy()).descending();
		Pageable pageable = PageRequest.of(criteria.getOffset(), criteria.getMax(), sort);

		Page<Employee> empPage = employeeRepository.findAll(pageable);

		responseVO.setMessage(Constants.STATUS_SUCCESS);
		responseVO.setTotalRecords(empPage.getTotalElements());
		responseVO.setPageNumber(empPage.getNumber() + 1);
		responseVO.setPageSize(empPage.getSize());
		responseVO.setData(empPage.getContent());

		return responseVO;
	}

	public Optional<Employee> findById(Long id) {
		return employeeRepository.findById(id);
	}

	public Employee save(Employee employee) {
		return employeeRepository.save(employee);
	}

	public boolean existsById(Long id) {
		return employeeRepository.existsById(id);
	}

	public void deleteById(Long id) {
		employeeRepository.deleteById(id);
		
	}
	
	public List<Employee> findEmployeeByNameOrPhoneNumber(String searchQuery){
		return employeeRepository.findWhereNameOrPhoneNumberLike(searchQuery);
	}
}
