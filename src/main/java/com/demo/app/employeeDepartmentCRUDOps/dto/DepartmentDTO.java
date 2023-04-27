package com.demo.app.employeeDepartmentCRUDOps.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class DepartmentDTO {

	private Long deptId;
	
	@NotNull
	@Pattern(regexp = "^(?!\\s*$).+", message = "Department name must not be blank")	
	private String deptName;
	
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
}
