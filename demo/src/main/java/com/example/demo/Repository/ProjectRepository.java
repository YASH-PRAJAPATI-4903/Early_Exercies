package com.example.demo.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Map<String,Object>> getAllProject(){
		String sql="SELECT * FROM project";
		return jdbcTemplate.queryForList(sql);
	}
	
//	public Map<String,Object> getProjectById(Long id){
	public List<Map<String,Object>> getProjectById(Long id){
//		String sql="SELECT * FROM project WHERE id=?";
		String sql="SELECT p.id AS project_id,\r\n"
				+ "    p.name AS project_name,\r\n"
				+ "    p.description AS project_description,\r\n"
				+ "    p.is_active AS project_is_active , \r\n"
				+ "    p.created_time AS project_created_time,\r\n"
				+ "    p.updated_time AS project_updated_time, \r\n"
				+ "    \r\n"
				+ "    e.id AS employee_id,\r\n"
				+ "    e.name AS employee_name,\r\n"
				+ "    e.email AS employee_email,\r\n"
				+ "    e.birth_date AS employee_birth_date,\r\n"
				+ "    e.created_time AS employee_created_time,\r\n"
				+ "    e.updated_time AS employee_updated_time,\r\n"
				+ "    e.department_id AS employee_department_id FROM\r\n"
				+ "project AS p INNER JOIN project_team_member AS pt ON pt.project_id=p.id \r\n"
				+ "INNER JOIN employee AS e ON pt.employee_id=e.id where p.id = ?";
		return jdbcTemplate.queryForList(sql, id);
	}
	
//	public int createEmployee(String name, String description, Boolean is_active) {
//		String sql="INSERT INTO project(name, description, is_active) VALUES(?,?,?)";
//		return jdbcTemplate.update(sql, name, description, is_active);
//	}
	
	public long createEmployee(String name, String description, Boolean is_active) {
		String sql="INSERT INTO project(name, description, is_active) VALUES(?,?,?)";
		
		 KeyHolder keyHolder = new GeneratedKeyHolder();

	        jdbcTemplate.update(connection -> {
	            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	            ps.setString(1, name);
	            ps.setString(2, description);
	            ps.setBoolean(3, is_active);
	            return ps;
	        }, keyHolder);
		
		return keyHolder.getKey().longValue();
	}
	
	public void insertProjectTeamMembers(Long projectId, List<Long> employeeIds) {
        String sql = "INSERT INTO project_team_member (project_id, employee_id) VALUES (?, ?)";
        for (Long empId : employeeIds) {
            jdbcTemplate.update(sql, projectId, empId);
        }
    }
	
	public int updateEmployee(Long id, String name, String description, Boolean is_active) {
		String sql="UPDATE project SET name=?, description=?, is_active=? WHERE id=?";
		jdbcTemplate.update(sql, name, description, is_active, id);
		String sql1="DELETE FROM project_team_member WHERE project_id=?";
		return jdbcTemplate.update(sql1, id);
	}
	
//	public int updateEmployee(Long id, String name, String description, Boolean is_active, List<Long> employee_id) {
//		String updateEmployeesql="UPDATE project SET name=?, description=?, is_active=? WHERE id=?";
//		jdbcTemplate.update(updateEmployeesql, name, description, is_active, id);
//		
//		String deletePEMsql="Delete project_team_member WHERE project_id=?";
//		return jdbcTemplate.update(deletePEMsql, id);
//	}
	
	public int deleteEmployee(Long id) {
		String sql="DELETE FROM project WHERE id=?";
		String sql1="DELETE FROM project_team_member WHERE project_id=?";
		jdbcTemplate.update(sql1,  id);
		return jdbcTemplate.update(sql, id);
	}
	
}
