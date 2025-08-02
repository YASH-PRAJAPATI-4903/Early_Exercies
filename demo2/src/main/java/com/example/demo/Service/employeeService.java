package com.example.demo.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Department;
import com.example.demo.Entity.Employee;
import com.example.demo.Entity.Project;
import com.example.demo.Entity.ProjectTeamMember;
import com.example.demo.Repository.EmployeeRepository;
import com.example.demo.Repository.ProjectRepository;
import com.example.demo.Repository.ProjectTeamMemberRepository;
import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.EmployeeWithProjectsDTO;
import com.example.demo.dto.ProjectDTO;

@Service
public class employeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectTeamMemberRepository projectTeamMemberRepository;

    @Autowired
    private ProjectRepository projectRepository;
	
	public List<Employee> getAllEmployee(){
		return employeeRepository.findAll();
	}
	
//	public Optional<employee> getEmployeeById(Long id){
//		return employeeRepository.findById(id);
//	}
	
	public EmployeeWithProjectsDTO getEmployeeById(Long id){
		
		Employee employee= employeeRepository.findById(id).orElseThrow(()-> new RuntimeException ("Employee not found by given Id."));
		
		Department dept = employee.getDepartment();
		
		DepartmentDTO departmentDTO=new DepartmentDTO();
		departmentDTO.setDepartmentId(dept.getId());
		departmentDTO.setName(dept.getName());
		departmentDTO.setDescription(dept.getDescription());
		
		List<ProjectTeamMember> teamMembers= projectTeamMemberRepository.findById_EmployeeId(id);
		
		List<ProjectDTO> projectDTOs= teamMembers.stream()
										.map(ptm -> {
											Project project = projectRepository.findById(ptm.getId().getProjectId())
													.orElseThrow(()-> new RuntimeException("Project Not found by given id!!!"));
											ProjectDTO dto= new ProjectDTO();
											dto.setProjectId(project.getId());
											dto.setName(project.getName());
											dto.setDescription(project.getDescription());
											dto.setIsActive(project.getIs_active());
											return dto;
										}).collect(Collectors.toList());
		
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEmployeeId(employee.getId());
		employeeDTO.setName(employee.getName());
		employeeDTO.setEmail(employee.getEmail());
		
		EmployeeWithProjectsDTO employeeWithProjectsDTO = new EmployeeWithProjectsDTO();
		employeeWithProjectsDTO.setEmployeeDTO(employeeDTO);
		employeeWithProjectsDTO.setDepartment(departmentDTO);
		employeeWithProjectsDTO.setProjects(projectDTOs);
		
		return employeeWithProjectsDTO;
	}
	
	public Employee createEmployee(Employee e){		
		return employeeRepository.save(e);
	}
	
	public Employee updateEmployee(Long id, Employee e){
		
		return employeeRepository.findById(id).map(ex->{
			ex.setName(e.getName());
			ex.setEmail(e.getEmail());
			ex.setBirth_date(e.getBirth_date());
			ex.setDepartment(e.getDepartment());
			
			return employeeRepository.save(ex);
		}).orElseThrow(()->new RuntimeException("Employee not found!!!!"));
	}
	
	public void deleteEmployee(Long id){
		employeeRepository.deleteById(id);
	}
	
}
