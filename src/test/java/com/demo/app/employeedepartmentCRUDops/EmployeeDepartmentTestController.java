package com.demo.app.employeedepartmentCRUDops;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.demo.app.employeeDepartmentCRUDOps.model.Department;
import com.demo.app.employeeDepartmentCRUDOps.model.Employee;
import com.demo.app.employeeDepartmentCRUDOps.service.DepartmentService;
import com.demo.app.employeeDepartmentCRUDOps.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeDepartmentTestController {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DepartmentService deptService;
	
	@MockBean
	private EmployeeService empService;
	
	final static MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));

	@Test
	public void getDepartmentByIdTest() throws Exception {

		Long deptIdToFind = 5L;
		Department dept = new Department();
		dept.setDeptId(5L);
		dept.setDeptName("Production");

		String expectedOutputJsonStr = "{\"statusCode\":200,\"statusMessage\":\"success\",\"message\":\"success\",\"data\":{\"deptId\":5,\"deptName\":\"Production\"}}";
		Mockito.when(deptService.findById(any(Long.class))).thenReturn(Optional.ofNullable(dept));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/department/getDepartment/" + deptIdToFind))
				.andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(content().json(expectedOutputJsonStr))
				.andDo(MockMvcResultHandlers.print());
	}
	

	@Test
	public void addEmployeeTest() throws Exception {

		
        
		String inputJsonString = "{\"empName\":\"Nitin\",\"city\":\"Pune\",\"phoneNumber\":\"+5544433\",\"gender\":\"Male\",\"departmentList\":[\"IT\"]}";
		String expectedOutputJsonString = "{\"statusCode\":200,\"statusMessage\":\"success\",\"message\":\"Employee has been saved successfully\",\"data\":{\"empId\":2,\"empName\":\"Nitin\",\"city\":\"Pune\",\"phoneNumber\":\"+5544433\",\"gender\":\"Male\",\"departments\":[{\"deptId\":2,\"deptName\":\"IT\"}]}}";
		String empOutputObjectStr = "{\"empId\":2,\"empName\":\"Nitin\",\"city\":\"Pune\",\"phoneNumber\":\"+5544433\",\"gender\":\"Male\",\"departments\":[{\"deptId\":2,\"deptName\":\"IT\"}]}";

		Department dept = new Department();
		dept.setDeptId(2L);
		dept.setDeptName("IT");
		
		ObjectMapper outputObjMapper = new ObjectMapper();
		Employee empObj = outputObjMapper.readValue(empOutputObjectStr, Employee.class);
		
		Mockito.when(empService.save(any(Employee.class))).thenReturn(empObj);
		Mockito.when(deptService.findByDeptName(any(String.class))).thenReturn(dept);
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/employee/add/")
				.contentType(MEDIA_TYPE_JSON_UTF8).content(inputJsonString))
				.andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(content().json(expectedOutputJsonString))
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void addEmployeeNullOrBlankDataTest() throws Exception {

		
        
		String inputJsonString = "{\"empName\":\"\",\"city\":\"Pune\",\"phoneNumber\":\"+5544433\",\"gender\":\"\",\"departmentList\":[\"IT\"]}";
		String expectedOutputJsonString = "{\"statusCode\":200,\"statusMessage\":\"failed\",\"message\":\"Employee Name and Gender are mandatory field\",\"data\":null}";
		String empOutputObjectStr = "{\"empId\":1,\"empName\":\"Nitin\",\"city\":\"Pune\",\"phoneNumber\":\"+5544433\",\"gender\":\"Male\",\"departments\":[{\"deptId\":2,\"deptName\":\"IT\"}]}";

		Department dept = new Department();
		dept.setDeptId(2L);
		dept.setDeptName("IT");
		
		ObjectMapper outputObjMapper = new ObjectMapper();
		Employee empObj = outputObjMapper.readValue(empOutputObjectStr, Employee.class);
		
		Mockito.when(empService.save(any(Employee.class))).thenReturn(empObj);
		Mockito.when(deptService.findByDeptName(any(String.class))).thenReturn(dept);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/employee/add/")
				.contentType(MEDIA_TYPE_JSON_UTF8).content(inputJsonString))
				.andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(content().json(expectedOutputJsonString))
				.andDo(MockMvcResultHandlers.print());
	}
	
	
}
