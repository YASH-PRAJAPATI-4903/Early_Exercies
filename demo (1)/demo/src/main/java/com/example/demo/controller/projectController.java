package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.ProjectRepository;

@RestController
@RequestMapping("/api/projects")
public class projectController {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@GetMapping("/")
	public ResponseEntity<?> getAllProject(){
		List<Map<String, Object>> projects= projectRepository.getAllProject();
		if(projects.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Project Found");
		}
		return ResponseEntity.ok(projects);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getProjectById(@PathVariable Long id){
//		Map<String, Object> project= projectRepository.getProjectById(id);
		List<Map<String, Object>> project= projectRepository.getProjectById(id);
		
		if(project.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No project find by given id");
		}
		
		return ResponseEntity.ok(project);
	}
	
//	@PostMapping("/")
//	public ResponseEntity<?> createProject(@RequestParam String name, @RequestParam String description, @RequestParam Boolean is_active){
//		
//		try {
//			projectRepository.createEmployee(name, description, is_active);
//			
//			// Build a response body
//	        Map<String, Object> response = Map.of(
//	            "message", "Project added successfully"
//	        );
//	        return new ResponseEntity<>(response, HttpStatus.CREATED);
//
//		}catch (Exception e) {
//	        e.printStackTrace();
//	        Map<String, Object> error = Map.of(
//	            "error", "Failed to add project",
//	            "details", e.getMessage()
//	        );
//	        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		
//	}
	
	@PostMapping("/")
	public ResponseEntity<?> createProject(@RequestParam String name, @RequestParam String description, @RequestParam Boolean is_active, @RequestParam List<Long> employee_id){
		
		try {
			Long projectId =projectRepository.createEmployee(name, description, is_active);
			projectRepository.insertProjectTeamMembers(projectId, employee_id);
			
			// Build a response body
	        Map<String, Object> response = Map.of(
	            "message", "Project added successfully",
	            "project_id", projectId
	        );
	        return new ResponseEntity<>(response, HttpStatus.CREATED);

		}catch (Exception e) {
	        e.printStackTrace();
	        Map<String, Object> error = Map.of(
	            "error", "Failed to add project",
	            "details", e.getMessage()
	        );
	        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
//	@PutMapping("/{id}")
//	public ResponseEntity<String> updateProject(@PathVariable Long id, @RequestParam String name, @RequestParam String description, @RequestParam Boolean is_active){
//		projectRepository.updateEmployee(id, name, description, is_active);
//		
//		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Project updated.");
//	}
	@PutMapping("/{id}")
	public ResponseEntity<String> updateProject(@PathVariable Long id, @RequestParam String name, @RequestParam String description, @RequestParam Boolean is_active, @RequestParam List<Long> employee_id){
		projectRepository.updateEmployee(id, name, description, is_active);
		projectRepository.insertProjectTeamMembers(id, employee_id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Project updated.");
	}
	
//	@DeleteMapping("/{id}")
//	public ResponseEntity<String> deleteProject(@PathVariable Long id){
//		projectRepository.deleteEmployee(id);
//		
//		return ResponseEntity.ok("Project Deleted.");
//	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProject(@PathVariable Long id){
		projectRepository.deleteEmployee(id);
		
		return ResponseEntity.ok("Project Deleted.");
	}
	
}
