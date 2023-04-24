package com.demo.app.employeeDepartmentCRUDOps.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.demo.app.employeeDepartmentCRUDOps.dto.ResponseVO;
import com.demo.app.employeeDepartmentCRUDOps.dto.SearchCriteria;
import com.demo.app.employeeDepartmentCRUDOps.model.Department;
import com.demo.app.employeeDepartmentCRUDOps.service.DepartmentService;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

	@Autowired
	DepartmentService departmentService;

	@Autowired
	ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	@PostMapping("/add")
	public ResponseVO addNewDepartment(@RequestBody DepartmentDTO dept) {
		
		ResponseVO responseVO;
		if(dept.getDeptName() != null && !dept.getDeptName().isEmpty()) {
			Department department = modelMapper.map(dept, Department.class);

			logger.info("Add Department===>" + department.toString());
			
			if(!departmentService.existsByDeptName(dept.getDeptName())){
				
				Department savedDept = departmentService.save(department);
				responseVO =  new ResponseVO(Constants.STATUS_OK, Constants.STATUS_SUCCESS, Constants.DEPT_ADD_SUCCESS_MESSAGE, savedDept);
			}else {
				responseVO =  new ResponseVO(Constants.STATUS_OK, Constants.STATUS_FAILED, Constants.DEPT_ALREADY_EXIST, null);
			}
			
		}else {
			responseVO =  new ResponseVO(Constants.STATUS_OK, Constants.STATUS_FAILED, Constants.DEPT_NAME_EMPTY, null);
		}
		
		return responseVO;
	}

	@GetMapping("/getDepartment/{id}")
	public ResponseVO getDepartment(@PathVariable("id") Long id) {

		ResponseVO responseVO;
		Optional<Department> dept = departmentService.findById(id);
		if(dept.isPresent()) {
			responseVO = new ResponseVO(Constants.STATUS_OK, Constants.STATUS_SUCCESS, Constants.STATUS_SUCCESS, dept);
		}else {
			responseVO = new ResponseVO(Constants.STATUS_NOT_FOUND, Constants.STATUS_FAILED, Constants.RECORD_DOES_NOT_FOUND, dept);
		}

		return responseVO;

	}

	@GetMapping("/getAllDepartments")
	public AllDataResponseVO getDepartment(@RequestBody SearchCriteria criteria) {
		
		AllDataResponseVO responseVO = new AllDataResponseVO();
		Sort sort = criteria.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name())
				? Sort.by(criteria.getSortBy()).ascending()
				: Sort.by(criteria.getSortBy()).descending();
		Pageable pageable = PageRequest.of(criteria.getOffset(), criteria.getMax(), sort);
		 
		Page<Department> empPage= departmentService.findAll(pageable);
		
		responseVO.setStatusCode(Constants.STATUS_OK);
		responseVO.setMessage(Constants.STATUS_SUCCESS);
		responseVO.setTotalRecords(empPage.getTotalElements());
		responseVO.setPageNumber(empPage.getNumber() + 1);
		responseVO.setPageSize(empPage.getSize());
		responseVO.setData(
				empPage.getContent().stream()
				.peek(dept-> dept.setDeptName(dept.getDeptName().toUpperCase()))
				.collect(Collectors.toList())
				);
		
		return  responseVO;
	}

	@PutMapping("/update")
	public ResponseVO updateDepartment(@RequestBody DepartmentDTO dept) {

		ResponseVO responseVO;
		if((dept.getDeptId() != null) &&
				(dept.getDeptName() != null && !dept.getDeptName().isEmpty())) {
			
			if(departmentService.existsById(dept.getDeptId())) {
				
				Department department = modelMapper.map(dept, Department.class);

				logger.info("Update Department===>" + department.toString());
				Department savedDept = departmentService.save(department);
				responseVO =  new ResponseVO(Constants.STATUS_OK, Constants.STATUS_SUCCESS, Constants.DEPT_UPDATE_SUCCESS_MESSAGE, savedDept);
			}else {
				
				responseVO =  new ResponseVO(Constants.STATUS_OK, Constants.STATUS_FAILED, Constants.DEPT_NOT_EXIST_TO_UPDATE, null);
			}
			
		}else {
			responseVO =  new ResponseVO(Constants.STATUS_OK, Constants.STATUS_FAILED, Constants.DEPT_NAME_EMPTY, null);
		}
		
		return responseVO;
	}

	@DeleteMapping("/delete/{id}")
	public ResponseVO deleteDepartment(@PathVariable("id") Long id) throws SQLIntegrityConstraintViolationException {
		ResponseVO responseVO = null;
		if(departmentService.existsById(id)) {
			departmentService.deleteById(id);
			responseVO = new ResponseVO(Constants.STATUS_OK, Constants.STATUS_SUCCESS, Constants.DEPT_REMOVE_SUCCESS_MESSAGE, null);
			logger.info(Constants.DEPT_UPDATE_SUCCESS_MESSAGE);
		}else {
			responseVO = new ResponseVO(Constants.STATUS_NOT_FOUND, Constants.STATUS_FAILED, Constants.RECORD_DOES_NOT_FOUND, null);
		}

		return responseVO;

	}
}
