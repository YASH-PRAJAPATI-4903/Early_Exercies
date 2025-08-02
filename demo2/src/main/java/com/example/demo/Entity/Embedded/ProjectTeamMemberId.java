package com.example.demo.Entity.Embedded;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProjectTeamMemberId implements Serializable {
	
	@Column(name = "project_id")
	private Long projectId;
	
	@Column(name = "employee_id")
	private Long employeeId;
	
	@Override
	public boolean equals(Object o) {
		if(this == o) 
			return true;
		if(!(o instanceof ProjectTeamMemberId))
			return false;
		ProjectTeamMemberId that = (ProjectTeamMemberId) o;
		return Objects.equals(projectId, that.projectId) &&
			   Objects.equals(employeeId, that.employeeId);
	}
	
	public int hashCode() {
		return Objects.hash(projectId,employeeId);
	}
	
}
