package com.demo.app.employeeDepartmentCRUDOps.constants;

import java.util.HashMap;
import java.util.Map;

public final class Constants {


	  public static final String DELETE_DEPT_SQL_INTEGRITY_VIOLATION = "This department can not be deleted, Some employees are associated with it.";
	  public static final String DEPT_NOT_EXIST = " department does not exists, please create this department first and then add the employee";
	  public static final String STATUS_MESSAGE = "message";
	  public static final String STATUS_TIMESTAMP = "timestamp";
	  public static final String STATUS_ERROR = "errors";
	  public static final String STATUS_CODE = "statusCode";
	  public static final Integer STATUS_OK = 200;
	  public static final Integer STATUS_INERNAL_SERVER_ERROR = 500;
	  public static final Integer STATUS_NOT_FOUND = 404;
	  public static final String STATUS_SUCCESS = "success";
	  public static final String STATUS_FAILED = "failed";
	  public static final String DEPT_ADD_SUCCESS_MESSAGE = "Department has been saved successfully";
	  public static final String DEPT_UPDATE_SUCCESS_MESSAGE = "Department has been updated successfully";
	  public static final String DEPT_REMOVE_SUCCESS_MESSAGE = "Department has been deleted successfully";
	  public static final String EMP_ADD_SUCCESS_MESSAGE = "Employee has been saved successfully";
	  public static final String EMP_UPDATE_SUCCESS_MESSAGE = "Employee has been updated successfully";
	  public static final String EMP_REMOVE_SUCCESS_MESSAGE = "Employee has been deleted successfully";
	  public static final String RECORD_DOES_NOT_FOUND = "Record doesn't exists with the the given Id";
	  
	  public static final String DEPT_NAME_EMPTY = "Department Name is mandatory field";
	  public static final String EMP_NAME_GENDER_EMPTY = "Employee Name and Gender are mandatory field";
	  public static final String DEPT_ALREADY_EXIST = "Department is already exists";
	  public static final String DEPT_NOT_EXIST_TO_UPDATE = "Department does't found with given Id";
	  
	  
	  public static final String TOPIC_EXCHANGE_NAME = "add-emp-topic";

	  public static final String NEW_EMPLOYEE_QUEUE = "new-employee";

	  public static final String ACTION_ADD = "ADD";
	  public static final String ACTION_UPDATE = "UPDATE";
	  
	  public static final Map<String, String> EXCHANGE_QUEUE_MAPPING = new HashMap<String, String>()
	  {
	      {
	          put("add-emp-topic", "new-employee");
	          put("add-emp-topic", "new-emp-bgv");
	     
	      };
	  };

	 

		  
}