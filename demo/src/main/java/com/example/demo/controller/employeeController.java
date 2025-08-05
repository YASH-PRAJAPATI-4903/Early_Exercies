package com.example.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.EmployeeRepository;

@RestController
@RequestMapping("/api/employees")
public class employeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	
	@GetMapping("/")
	public ResponseEntity<?> getAllEmployee(){
		List<Map<String,Object>> employees= employeeRepository.getAllEmployee();
		
		if(employees.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("no Employees found");
		}
		
		return ResponseEntity.ok(employees);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getEmployeeById(@PathVariable Long id){
		
		Map<String, Object> employee=employeeRepository.getEmployeeById(id);
		
		if(employee.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Employee found by given id.");
		}
		
		return ResponseEntity.ok(employee);
	}
	
	@PostMapping("/")
	public ResponseEntity<String> createEmployee(@RequestParam String name,@RequestParam String email,@RequestParam Date birth_date,@RequestParam Long department_id){
		employeeRepository.createEmployee(name, email, birth_date, department_id);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("New Employee Created");
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateEmployee(@PathVariable Long id, @RequestParam String name, @RequestParam String email, @RequestParam Date birth_date, @RequestParam Long department_id){
		employeeRepository.updateEmployee(id, name, email, birth_date, department_id);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Employee Updated");

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
		employeeRepository.deleteEmployee(id);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Employee deleted.");

	}
	
}
