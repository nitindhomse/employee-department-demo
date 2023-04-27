package com.demo.app.employeeDepartmentCRUDOps.dto;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;

public class EmployeeDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long empId;
	
	@NotNull
	@Pattern(regexp = "^(?!\\s*$).+", message = "Employee Name must not be blank")	
	private String empName;
	
	private String city;
	
	@Pattern(regexp="(^$|[0-9]{10})", message = "Please enter valid phone number")
	private String phoneNumber;
	
	@NotNull
	@Pattern(regexp = "^(?!\\s*$).+", message = "Gender field must not be blank")
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
	
	@Override
	public String toString() {
		return "EmployeeDTO [empId=" + empId + ", empName=" + empName + ", city=" + city + ", phoneNumber="
				+ phoneNumber + ", gender=" + gender + ", departmentList=" + departmentList + "]";
	}
	
	
}
