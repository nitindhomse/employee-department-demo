package com.demo.app.employeeDepartmentCRUDOps.exception;

public class DuplicateDepartmentException extends RuntimeException  {

	private static final long serialVersionUID = 1L;

	public DuplicateDepartmentException(String errorMessage) {
        super(errorMessage);
    }
}