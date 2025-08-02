package com.example.demo.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> getAllDepartment(){
		String sql="SELECT * FROM department";
		return jdbcTemplate.queryForList(sql);
	}
	
	public Map<String, Object> getDepartmentById(Long id){
		String sql="SELECT * FROM department where id = ?";
		return jdbcTemplate.queryForMap(sql, id);
	}
	
	public int createDepartment(String name, String description) {
		String sql="INSERT INTO department(name,description) VALUES(?,?)";
		return jdbcTemplate.update(sql,name,description);
	}
	
	public int updateDepartment(Long id, String name, String description) {
		String sql="UPDATE department SET name=?, description=? WHERE id=?";
		return jdbcTemplate.update(sql,name,description,id);
	}
	
	public int deleteDepartment(Long id) {
		String sql="DELETE FROM department WHERE id=?";
		return jdbcTemplate.update(sql,id);
	}
	
}

