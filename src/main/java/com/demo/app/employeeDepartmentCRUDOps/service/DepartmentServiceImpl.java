package com.demo.app.employeeDepartmentCRUDOps.service;

import com.demo.app.employeeDepartmentCRUDOps.dto.AllDataResponseVO;
import com.demo.app.employeeDepartmentCRUDOps.dto.SearchCriteria;

public interface DepartmentServiceImpl {

	public AllDataResponseVO getAllDepartmentList(SearchCriteria criteria);
}
