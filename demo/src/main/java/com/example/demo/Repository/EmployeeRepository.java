package com.example.demo.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> getAllEmployee(){
		String sql="SELECT * FROM employee";
		return jdbcTemplate.queryForList(sql);
	}
	
	public Map<String, Object> getEmployeeById(Long id){
//		String sql="SELECT * FROM employee where id = ?";
		String sql="SELECT e.id AS employee_id,\r\n"
				+ "    e.name AS employee_name,\r\n"
				+ "    e.email AS employee_email,\r\n"
				+ "    e.birth_date AS employee_birth_date,\r\n"
				+ "    e.created_time AS employee_created_time,\r\n"
				+ "    e.updated_time AS employee_updated_time,\r\n"
				+ "    e.department_id AS employee_department_id,\r\n"
				+ "    \r\n"
				+ "    d.id AS department_id,\r\n"
				+ "    d.name AS department_name,\r\n"
				+ "    d.description AS department_description,\r\n"
				+ "    d.created_time AS department_created_time,\r\n"
				+ "    d.updated_time AS department_updated_time,\r\n"
				+ "    \r\n"
				+ "    p.id AS project_id,\r\n"
				+ "    p.name AS project_name,\r\n"
				+ "    p.description AS project_description,\r\n"
				+ "    p.is_active AS project_is_active , \r\n"
				+ "    p.created_time AS project_created_time,\r\n"
				+ "    p.updated_time AS project_updated_time FROM \r\n"
				+ "department AS d INNER JOIN employee AS e ON d.id = e.department_id \r\n"
				+ "INNER JOIN project_team_member AS pt ON e.id = pt.employee_id\r\n"
				+ "INNER JOIN project AS p ON pt.project_id=p.id where e.id=?";
		return jdbcTemplate.queryForMap(sql, id);
	}
	
	public int createEmployee(String name, String email, Date birth_date, Long department_id) {
		String sql="INSERT INTO employee(name, email, birth_date, department_id) VALUES(?,?,?,?)";
		return jdbcTemplate.update(sql,name, email, birth_date, department_id);
	}
	
	public int updateEmployee(Long id, String name, String email, Date birth_date, Long department_id) {
		String sql="UPDATE employee SET name=?, email=?, birth_date=?, department_id=? WHERE id=?";
		return jdbcTemplate.update(sql, name, email, birth_date, department_id, id);
	}
	
	public int deleteEmployee(Long id) {
		String sql="DELETE FROM employee WHERE id=?";
		return jdbcTemplate.update(sql,id);
	}
	
}
