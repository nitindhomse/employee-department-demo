package com.demo.app.employeeDepartmentCRUDOps.exception;

public class EmployeeNotExistsException extends RuntimeException  {

	private static final long serialVersionUID = 1L;

	public EmployeeNotExistsException(String errorMessage) {
        super(errorMessage);
    }
}
