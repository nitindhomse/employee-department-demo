package com.demo.app.employeeDepartmentCRUDOps.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.demo.app.employeeDepartmentCRUDOps.constants.Constants;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	final static Logger logger = LoggerFactory.getLogger(ControllerAdvisor.class);

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(InvalidDepartmentException.class)
	public ResponseEntity<Object> handleInvalidDepartment(InvalidDepartmentException ex, WebRequest request) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorMessage(ex.getMessage());
		errorResponse.setTimestamp(LocalDateTime.now().toString());

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EmployeeNotExistsException.class)
	public ResponseEntity<Object> handleInvalidEmployee(EmployeeNotExistsException ex, WebRequest request) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorMessage(ex.getMessage());
		errorResponse.setTimestamp(LocalDateTime.now().toString());

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<Object> handleDeleteDeptartmentException(SQLIntegrityConstraintViolationException ex,
			WebRequest request) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorMessage(Constants.DELETE_DEPT_SQL_INTEGRITY_VIOLATION);
		errorResponse.setTimestamp(LocalDateTime.now().toString());

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(DuplicateDepartmentException.class)
	public ResponseEntity<Object> handleDuplicateDepartment(DuplicateDepartmentException ex, WebRequest request) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorMessage(ex.getMessage());
		errorResponse.setTimestamp(LocalDateTime.now().toString());

		return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	}
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleotherExceptions(Exception ex, WebRequest request) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorMessage(ex.getMessage());
		errorResponse.setTimestamp(LocalDateTime.now().toString());

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, List<String>> body = new HashMap<>();

		List<String> errors = ex.getBindingResult().getFieldErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());

		body.put("errors", errors);

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	public void logError(Exception ex) {
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		logger.error("Exception Message => {} ", ex.getMessage());
		logger.error("Exception Stacktrace => {} ", sw.toString());
	}

}