package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.ProjectTeamMember;
import com.example.demo.Service.projectTeamMemberService;

@RestController
@RequestMapping("/api/project_teams")
public class ProjectTeamMemberController {
	
	@Autowired
	private projectTeamMemberService projectTeamMemberService; 
	
	@GetMapping
	public ResponseEntity<?> getAllProjectTeamMember(){
		List<ProjectTeamMember> projectTeam = projectTeamMemberService.getAllProjectTeamMember();
		
		if(projectTeam.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Not a single Project_Team.");
		}
		return ResponseEntity.ok(projectTeam);
	}
	
	@PostMapping
	public ResponseEntity<String> createProjectTeamMember(@RequestBody ProjectTeamMember ptm){
		projectTeamMemberService.createProjectTeamMember(ptm);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Add Project Team.");
	}
	
	@DeleteMapping("/{project_id}")
	public ResponseEntity<String> deleteProjectTeamMember(@PathVariable Long project_id){
		projectTeamMemberService.deleteProjectTeamMember(project_id);
		return ResponseEntity.ok("Delete Project Team.");
	}
	
}
