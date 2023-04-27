package com.demo.app.employeeDepartmentCRUDOps.dao;

import java.util.HashSet;
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
import org.springframework.util.CollectionUtils;
import com.demo.app.employeeDepartmentCRUDOps.constants.Constants;
import com.demo.app.employeeDepartmentCRUDOps.dto.AllDataResponseVO;
import com.demo.app.employeeDepartmentCRUDOps.dto.EmployeeDTO;
import com.demo.app.employeeDepartmentCRUDOps.dto.SearchCriteria;
import com.demo.app.employeeDepartmentCRUDOps.exception.EmployeeNotExistsException;
import com.demo.app.employeeDepartmentCRUDOps.exception.InvalidDepartmentException;
import com.demo.app.employeeDepartmentCRUDOps.model.Department;
import com.demo.app.employeeDepartmentCRUDOps.model.Employee;
import com.demo.app.employeeDepartmentCRUDOps.service.DepartmentService;
import com.demo.app.employeeDepartmentCRUDOps.service.EmployeeService;
import com.demo.app.employeeDepartmentCRUDOps.service.EmployeeServiceImpl;

@Service
public class EmployeeServiceDAOImpl implements EmployeeServiceImpl {

	private final Logger logger = LoggerFactory.getLogger(EmployeeServiceDAOImpl.class);

	private final DepartmentService departmentService;
	private final ModelMapper modelMapper;
	private final EmployeeService employeeService;

	public EmployeeServiceDAOImpl(DepartmentService departmentService, ModelMapper modelMapper,
			EmployeeService employeeService) {
		this.departmentService = departmentService;
		this.modelMapper = modelMapper;
		this.employeeService = employeeService;
	}

	@Override
	public Optional<Employee> buildEmployee(EmployeeDTO employee, String action) {

		boolean isExists = false;
		Employee emp = modelMapper.map(employee, Employee.class);
		Set<Department> deptSet = new HashSet<>();

		if (action.equals(Constants.ACTION_UPDATE)) {
			isExists = employeeService.existsById(employee.getEmpId());
		}
		if (action.equals(Constants.ACTION_ADD) || isExists) {

			if (!CollectionUtils.isEmpty(employee.getDepartmentList()))
				employee.getDepartmentList().forEach(deptName -> {
					Optional<Department> dept = departmentService.findByDeptName(deptName);
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

	@Override
	public AllDataResponseVO getAllEmployeesList(SearchCriteria criteria) {

		AllDataResponseVO responseVO = new AllDataResponseVO();
		Sort sort = criteria.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name())
				? Sort.by(criteria.getSortBy()).ascending()
				: Sort.by(criteria.getSortBy()).descending();
		Pageable pageable = PageRequest.of(criteria.getOffset(), criteria.getMax(), sort);

		Page<Employee> empPage = employeeService.findAll(pageable);

		responseVO.setMessage(Constants.STATUS_SUCCESS);
		responseVO.setTotalRecords(empPage.getTotalElements());
		responseVO.setPageNumber(empPage.getNumber() + 1);
		responseVO.setPageSize(empPage.getSize());
		responseVO.setData(empPage.getContent());

		return responseVO;
	}
}
