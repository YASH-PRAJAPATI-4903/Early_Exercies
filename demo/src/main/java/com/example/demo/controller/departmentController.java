package com.example.demo.controller;

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

import com.example.demo.Repository.DepartmentRepository;



@RestController
@RequestMapping("/api/departments")
public class departmentController {
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@GetMapping("/")
	public ResponseEntity<?> getAllDepartments(){
		List<Map<String,Object>> departments= departmentRepository.getAllDepartment();
		
		if(departments.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("no Deparments found");
		}
		
		return ResponseEntity.ok(departments);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getDepartmentById(@PathVariable Long id){
		
		Map<String, Object> department=departmentRepository.getDepartmentById(id);
		
		if(department.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Deparment found by given id.");
		}
		
		return ResponseEntity.ok(department);
	}
	
	@PostMapping("/")
	public ResponseEntity<String> createDepartment(@RequestParam String name,@RequestParam String description){
		departmentRepository.createDepartment(name, description);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("New Department Created");
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateDepartment(@PathVariable Long id, @RequestParam String name,@RequestParam String description){
		departmentRepository.updateDepartment(id, name, description);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Department Updated");

	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteDepartment(@PathVariable Long id){
		departmentRepository.deleteDepartment(id);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Department deleted.");

	}
	
}
