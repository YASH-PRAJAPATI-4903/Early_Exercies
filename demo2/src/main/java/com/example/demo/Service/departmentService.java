package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Department;
import com.example.demo.Repository.DepartmentRepository;

@Service
public class departmentService {

	@Autowired
	private DepartmentRepository departmentRepository;
	
	public List<Department> getAllDepartments(){
		return departmentRepository.findAll();
	}
	
	public Optional<Department> getDepartmentById(Long id){
		return departmentRepository.findById(id);
	}
	
	public Department createDepartment(Department d){
		return departmentRepository.save(d);
	}
	
	public Department updateDepartment(Long id, Department d){
		
		return departmentRepository.findById(id).map(ex->{
			ex.setName(d.getName());
			ex.setDescription(d.getDescription());
			return departmentRepository.save(ex);
		}).orElseThrow(()->new  RuntimeException("Department not found!!!"));

	}
	
	public void deleteDepartment(Long id){
		departmentRepository.deleteById(id);
	}
	
}
