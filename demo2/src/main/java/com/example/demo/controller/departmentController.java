package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

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

import com.example.demo.Entity.Department;
import com.example.demo.Service.departmentService;



@RestController
@RequestMapping("/api/departments")
public class departmentController{
	
	@Autowired
	private departmentService departmentService;
	
	@GetMapping
	public ResponseEntity<?> getAllDepartments(){
		List<Department> departments= departmentService.getAllDepartments();
		
		if(departments.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deparments not found");
		}
		
		return ResponseEntity.ok(departments);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getDepartmentById(@PathVariable Long id){
		
		Optional<Department> department=departmentService.getDepartmentById(id);
		
		if(department.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Deparment found by given id.");
		}
		
		return ResponseEntity.ok(department);
	}
	
	@PostMapping
	public ResponseEntity<String> createDepartment(@RequestBody Department d){
		departmentService.createDepartment(d);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("New Department Created");
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateDepartment(@PathVariable Long id, @RequestBody Department d){
		departmentService.updateDepartment(id,d);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Department Updated");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteDepartment(@PathVariable Long id){
		departmentService.deleteDepartment(id);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Department deleted.");
	}
	
}
