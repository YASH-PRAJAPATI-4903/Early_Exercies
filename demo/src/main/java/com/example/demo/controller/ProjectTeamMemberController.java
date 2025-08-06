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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.ProjectTeamMemberRepository;

@RestController
@RequestMapping("/api/project_teams")
public class ProjectTeamMemberController {
	
	@Autowired
	private ProjectTeamMemberRepository projectTeamMemberRepository; 
	
	@GetMapping("/")
	public ResponseEntity<?> getAllProjectTeamMember(){
		List<Map<String, Object>> projectTeam = projectTeamMemberRepository.getAllProjectTeamMember();
		
		if(projectTeam.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Not a single Project_Team.");
		}
		return ResponseEntity.ok(projectTeam);
	}
	
	@PostMapping("/")
	public ResponseEntity<String> createProjectTeamMember(@RequestParam Long project_id, @RequestParam Long employee_id){
		projectTeamMemberRepository.createProjectTeamMember(project_id, employee_id);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Add Project Team.");
	}
	
	@DeleteMapping("/{project_id}")
	public ResponseEntity<String> deleteProjectTeamMember(@PathVariable Long project_id){
		projectTeamMemberRepository.deleteProjectTeamMember(project_id);
		return ResponseEntity.ok("Delete Project Team.");
	}
	
}
