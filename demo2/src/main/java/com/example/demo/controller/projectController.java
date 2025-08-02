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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Project;
import com.example.demo.Service.projectService;
import com.example.demo.dto.ProjectWithEmployeesDTO;
import com.example.demo.dto.ProjectWithEmployeesIdsDTO;

@RestController
@RequestMapping("/api/projects")
public class projectController {
	
	@Autowired
	private projectService projectService;
	
	@GetMapping
	public ResponseEntity<?> getAllProject(){
		List<Project> projects= projectService.getAllProject();
		if(projects.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Project Found");
		}
		return ResponseEntity.ok(projects);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getProjectById(@PathVariable Long id){
		ProjectWithEmployeesDTO projectWithEmployeesDTO= projectService.getProjectById(id);
		
		if(projectWithEmployeesDTO.getProject().getProjectId()==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No project find by given id");
		}
		
		return ResponseEntity.ok(projectWithEmployeesDTO);
	}
	@PostMapping
	public ResponseEntity<?> createProject(@RequestBody ProjectWithEmployeesIdsDTO ptmId){
		
		try {
			projectService.createProject(ptmId);
			
			Map<String, String> response = Map.of(
		            "message", "Project added successfully"
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
	public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody ProjectWithEmployeesIdsDTO p){
		try {
			projectService.updateProject(id, p);
			Map<String, String> response = Map.of(
		            "message", "Project updated successfully"
		     );
		     return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

		}catch (Exception e) {
	        e.printStackTrace();
	        Map<String, Object> error = Map.of(
	            "error", "Failed to updated project",
	            "details", e.getMessage()
	        );
	        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	@DeleteMapping("/{id}")
//	public ResponseEntity<String> deleteProject(@PathVariable Long id){
//		projectRepository.deleteEmployee(id);
//		
//		return ResponseEntity.ok("Project Deleted.");
//	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProject(@PathVariable Long id){
		projectService.deleteProject(id);
		
		return ResponseEntity.ok("Project Deleted.");
	}
	
}
