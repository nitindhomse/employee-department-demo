package com.demo.app.employeeDepartmentCRUDOps.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.app.employeeDepartmentCRUDOps.constants.Constants;
import com.demo.app.employeeDepartmentCRUDOps.dao.DepartmentRepository;
import com.demo.app.employeeDepartmentCRUDOps.dto.AllDataResponseVO;
import com.demo.app.employeeDepartmentCRUDOps.dto.SearchCriteria;
import com.demo.app.employeeDepartmentCRUDOps.model.Department;

@Transactional
@Service
public class DepartmentService {

	private DepartmentRepository departmentRepository;

	DepartmentService(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	
	public AllDataResponseVO getAllDepartmentList(SearchCriteria criteria) {

		AllDataResponseVO responseVO = new AllDataResponseVO();
		Sort sort = criteria.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name())
				? Sort.by(criteria.getSortBy()).ascending()
				: Sort.by(criteria.getSortBy()).descending();
		Pageable pageable = PageRequest.of(criteria.getOffset(), criteria.getMax(), sort);

		Page<Department> empPage = departmentRepository.findAll(pageable);

		responseVO.setMessage(Constants.STATUS_SUCCESS);
		responseVO.setTotalRecords(empPage.getTotalElements());
		responseVO.setPageNumber(empPage.getNumber() + 1);
		responseVO.setPageSize(empPage.getSize());
		responseVO.setData(empPage.getContent().stream()
				.peek(dept -> dept.setDeptName(dept.getDeptName().toUpperCase())).collect(Collectors.toList()));

		return responseVO;
	}


	public Department save(Department department) {
		return departmentRepository.save(department);
	}


	public void deleteById(Long id) {
		departmentRepository.deleteById(id);
	}


	public boolean existsById(Long id) {
		return departmentRepository.existsById(id);
	}


	public Optional<Department> findById(Long id) {
		return departmentRepository.findById(id);
	}


	public boolean existsByDeptName(String deptName) {
		
		return departmentRepository.existsByDeptName(deptName);
	}


	public Optional<Department> findByDeptName(String deptName) {
		
		return departmentRepository.findByDeptName(deptName);
	}
}
