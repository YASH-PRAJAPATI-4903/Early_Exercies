package com.example.demo.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectTeamMemberRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> getAllProjectTeamMember(){
		String sql= "SELECT * FROM project_team_member";
		return jdbcTemplate.queryForList(sql);
	}
	
	public int createProjectTeamMember(Long project_id, Long employee_id) {
		String sql="INSERT INTO project_team_member(project_id, employee_id) VALUES(?,?)";
		return jdbcTemplate.update(sql,project_id,employee_id);
	}
	
	public int deleteProjectTeamMember(Long project_id) {
		String sql="DELETE FROM project_team_member WHERE project_id=?";
		return jdbcTemplate.update(sql, project_id);
	}
}
