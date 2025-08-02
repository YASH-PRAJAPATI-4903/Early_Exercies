package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Employee;
import com.example.demo.Service.employeeService;
import com.example.demo.dto.EmployeeWithProjectsDTO;

@RestController
@RequestMapping("/api/employees")
public class employeeController {
	
	@Autowired
	private employeeService employeeService;
	
	
	@GetMapping
	public ResponseEntity<?> getAllEmployee(){
		List<Employee> employees= employeeService.getAllEmployee();
		
		if(employees.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Employees not found");
		}
		
		return ResponseEntity.ok(employees);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getEmployeeById(@PathVariable Long id){
		EmployeeWithProjectsDTO employeeWithProjectsDTO=employeeService.getEmployeeById(id);
		
		if(employeeWithProjectsDTO.getEmployeeDTO().getEmployeeId()== null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Employee found by given id.");
		}
		
		return ResponseEntity.ok(employeeWithProjectsDTO);
	}
	
	@PostMapping
	public ResponseEntity<String> createEmployee(@RequestBody Employee e){
		employeeService.createEmployee(e);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("New Employee Created");
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateEmployee(@PathVariable Long id, @RequestBody Employee e){
		employeeService.updateEmployee(id, e);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Employee Updated");

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
		employeeService.deleteEmployee(id);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Employee deleted.");

	}
	
}
