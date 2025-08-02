package com.example.demo.Entity;

import com.example.demo.Entity.Embedded.ProjectTeamMemberId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="project_team_member")
public class ProjectTeamMember {
	
	@EmbeddedId
	private ProjectTeamMemberId id;
	
	@ManyToOne
	@MapsId("projectId")
	@JoinColumn(name= "project_id")
	private Project project;
	
	@ManyToOne
	@MapsId("employeeId")
	@JoinColumn(name= "employee_id")
	private Employee employee;
}
