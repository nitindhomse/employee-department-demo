package com.demo.app.employeeDepartmentCRUDOps.dao;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.demo.app.employeeDepartmentCRUDOps.constants.Constants;
import com.demo.app.employeeDepartmentCRUDOps.dto.AllDataResponseVO;
import com.demo.app.employeeDepartmentCRUDOps.dto.SearchCriteria;
import com.demo.app.employeeDepartmentCRUDOps.model.Department;
import com.demo.app.employeeDepartmentCRUDOps.service.DepartmentService;
import com.demo.app.employeeDepartmentCRUDOps.service.DepartmentServiceImpl;

@Service
public class DepartmentServiceDAOImpl implements DepartmentServiceImpl {

	private DepartmentService departmentService;

	DepartmentServiceDAOImpl(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Override
	public AllDataResponseVO getAllDepartmentList(SearchCriteria criteria) {

		AllDataResponseVO responseVO = new AllDataResponseVO();
		Sort sort = criteria.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name())
				? Sort.by(criteria.getSortBy()).ascending()
				: Sort.by(criteria.getSortBy()).descending();
		Pageable pageable = PageRequest.of(criteria.getOffset(), criteria.getMax(), sort);

		Page<Department> empPage = departmentService.findAll(pageable);

		responseVO.setMessage(Constants.STATUS_SUCCESS);
		responseVO.setTotalRecords(empPage.getTotalElements());
		responseVO.setPageNumber(empPage.getNumber() + 1);
		responseVO.setPageSize(empPage.getSize());
		responseVO.setData(empPage.getContent().stream()
				.peek(dept -> dept.setDeptName(dept.getDeptName().toUpperCase())).collect(Collectors.toList()));

		return responseVO;
	}

}
