package com.demo.app.employeeDepartmentCRUDOps.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.app.employeeDepartmentCRUDOps.constants.Constants;
import com.demo.app.employeeDepartmentCRUDOps.dto.AllDataResponseVO;
import com.demo.app.employeeDepartmentCRUDOps.dto.DepartmentDTO;
import com.demo.app.employeeDepartmentCRUDOps.dto.SearchCriteria;
import com.demo.app.employeeDepartmentCRUDOps.exception.DuplicateDepartmentException;
import com.demo.app.employeeDepartmentCRUDOps.exception.InvalidDepartmentException;
import com.demo.app.employeeDepartmentCRUDOps.model.Department;
import com.demo.app.employeeDepartmentCRUDOps.service.DepartmentService;
import com.demo.app.employeeDepartmentCRUDOps.service.DepartmentServiceImpl;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

	private final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	private DepartmentService departmentService;
	private DepartmentServiceImpl departmentServiceImpl;
	private ModelMapper modelMapper;

	public DepartmentController(DepartmentService departmentService, DepartmentServiceImpl departmentServiceImpl,
			ModelMapper modelMapper) {
		this.departmentService = departmentService;
		this.departmentServiceImpl = departmentServiceImpl;
		this.modelMapper = modelMapper;
	}

	@PostMapping("/")
	public Department create(@RequestBody DepartmentDTO dept) {

		Department department = modelMapper.map(dept, Department.class);
		logger.info("Add new Department {}" + dept.getDeptName());

		if (!departmentService.existsByDeptName(dept.getDeptName()))
			return departmentService.save(department);
		else
			throw new DuplicateDepartmentException("Duplicate Department : " + dept.getDeptName());

	}

	@GetMapping("/{id}")
	public Optional<Department> get(@PathVariable("id") Long id) {

		return departmentService.findById(id);

	}

	@PutMapping("/")
	public Department update(@RequestBody DepartmentDTO dept) {

		Department department = modelMapper.map(dept, Department.class);
		logger.info("Inside update Department {}", dept.getDeptId());
		return departmentService.save(department);

	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") Long id) throws SQLIntegrityConstraintViolationException {

		if (departmentService.existsById(id)) {
			departmentService.deleteById(id);
			return Constants.DEPT_REMOVE_SUCCESS_MESSAGE;
		} else {
			throw new InvalidDepartmentException("Department Id: " + id + " is not exists");
		}

	}

	@GetMapping("/getAllDepartments")
	public AllDataResponseVO getAllDepartments(@RequestBody SearchCriteria criteria) {

		return departmentServiceImpl.getAllDepartmentList(criteria);
	}

}
