package com.demo.app.employeedepartmentCRUDops;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
public class EmployeeDepartmentControllerTest {

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

		String expectedOutputJsonStr = "{\"deptId\":5,\"deptName\":\"Production\"}";
		Mockito.when(deptService.findById(any(Long.class))).thenReturn(Optional.ofNullable(dept));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/department/" + deptIdToFind))
				.andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(content().json(expectedOutputJsonStr))
				.andDo(MockMvcResultHandlers.print());
	}
	

	@Test
	public void addEmployeeTest() throws Exception {

		
        
		String inputJsonString = "{\"empName\":\"Archit\",\"city\":\"Nagpur\",\"phoneNumber\":\"1234567890\",\"gender\":\"Male\",\"departmentList\":[\"Production\"]}";
		String expectedOutputJsonString = "{\"empId\":20,\"empName\":\"Archit\",\"city\":\"Nagpur\",\"phoneNumber\":\"1234567890\",\"gender\":\"Male\",\"departments\":[{\"deptId\":20,\"deptName\":\"Production\"}]}";
		String empOutputObjectStr = "{\"empId\":20,\"empName\":\"Archit\",\"city\":\"Nagpur\",\"phoneNumber\":\"1234567890\",\"gender\":\"Male\",\"departments\":[{\"deptId\":20,\"deptName\":\"Production\"}]}";

		
		Department dept = new Department();
		dept.setDeptId(20L);
		dept.setDeptName("Production");
		Set<Department> deptSet = new HashSet<Department>();
		deptSet.add(dept);
		
		
		ObjectMapper outputObjMapper = new ObjectMapper();
		Employee empObj = outputObjMapper.readValue(empOutputObjectStr, Employee.class);
		empObj.setDepartments(deptSet);
		
		Mockito.when(empService.save(any(Employee.class))).thenReturn(empObj);
		Mockito.when(deptService.findByDeptName(any(String.class))).thenReturn(Optional.of(dept));
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/employee/")
				.contentType(MEDIA_TYPE_JSON_UTF8).content(inputJsonString))
				.andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(content().json(expectedOutputJsonString))
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void addEmployeeNullOrBlankDataTest() throws Exception {
  
		 
		String inputJsonString = "{\"city\":\"Nagpur\",\"phoneNumber\":\"1234567890\",\"gender\":\"\",\"departmentList\":[\"Production\"]}";
		String expectedOutputJsonString = "{\"errors\":[\"Employee Name must not be blank\"]}";
		
		Department dept = new Department();
		dept.setDeptId(20L);
		dept.setDeptName("Production");
		Set<Department> deptSet = new HashSet<Department>();
		deptSet.add(dept);
		
		
		ObjectMapper outputObjMapper = new ObjectMapper();
		Employee empObj = outputObjMapper.readValue(inputJsonString, Employee.class);
		empObj.setDepartments(deptSet);
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/employee/")
				.contentType(MEDIA_TYPE_JSON_UTF8).content(inputJsonString))
				.andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(content().json(expectedOutputJsonString))
				.andDo(MockMvcResultHandlers.print());
	}
	
	
}
