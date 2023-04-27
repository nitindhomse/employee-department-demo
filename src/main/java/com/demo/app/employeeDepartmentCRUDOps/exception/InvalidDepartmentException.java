package com.demo.app.employeeDepartmentCRUDOps.exception;

public class InvalidDepartmentException extends RuntimeException  { 

		private static final long serialVersionUID = 1L;

		public InvalidDepartmentException(String errorMessage) {
	        super(errorMessage);
	    }
}