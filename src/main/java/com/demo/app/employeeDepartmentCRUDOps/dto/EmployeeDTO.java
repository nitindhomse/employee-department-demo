package com.demo.app.employeeDepartmentCRUDOps.dto;

import java.util.Set;

public class EmployeeDTO {

	private Long empId;
	private String empName;
	private String city;
	private String phoneNumber;
	private String gender;
	private Set<String> departmentList;
	
	public Set<String> getDepartmentList() {
		return departmentList;
	}
	public void setDepartmentList(Set<String> departmentList) {
		this.departmentList = departmentList;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
}