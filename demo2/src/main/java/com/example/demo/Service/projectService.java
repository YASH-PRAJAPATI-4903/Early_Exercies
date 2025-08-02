package com.example.demo.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Employee;
import com.example.demo.Entity.Project;
import com.example.demo.Entity.ProjectTeamMember;
import com.example.demo.Entity.Embedded.ProjectTeamMemberId;
import com.example.demo.Repository.EmployeeRepository;
import com.example.demo.Repository.ProjectRepository;
import com.example.demo.Repository.ProjectTeamMemberRepository;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.ProjectDTO;
import com.example.demo.dto.ProjectWithEmployeesDTO;
import com.example.demo.dto.ProjectWithEmployeesIdsDTO;

@Service
public class projectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ProjectTeamMemberRepository projectTeamMemberRepository;
	
	
	public List<Project> getAllProject(){
		return projectRepository.findAll();
	}
	
	public ProjectWithEmployeesDTO getProjectById(Long id){
		
		Project project = projectRepository.findById(id).orElseThrow(()->new RuntimeException("Project not found by given id."));
		
		List<ProjectTeamMember> projectTeamMembers = projectTeamMemberRepository.findById_ProjectId(id);
		
		List<EmployeeDTO> employeeDTOs = projectTeamMembers.stream()
											.map(ptm-> {
												Employee employee = employeeRepository.findById(ptm.getId().getEmployeeId())
																.orElseThrow(()-> new RuntimeException("employee Not found by given id!!!"));
												
												EmployeeDTO employeeDTO=new EmployeeDTO();
												employeeDTO.setEmployeeId(employee.getId());
												employeeDTO.setName(employee.getName());
												employeeDTO.setEmail(employee.getEmail());
												
												return employeeDTO;
												
											}).collect(Collectors.toList());
		
		ProjectDTO projectDTO= new ProjectDTO();
		projectDTO.setProjectId(project.getId());
		projectDTO.setName(project.getName());
		projectDTO.setDescription(project.getDescription());
		projectDTO.setIsActive(project.getIs_active());
		
		ProjectWithEmployeesDTO projectWithEmployeesDTO = new ProjectWithEmployeesDTO();
		projectWithEmployeesDTO.setProject(projectDTO);	
		projectWithEmployeesDTO.setEmployees(employeeDTOs);
		
		
		return projectWithEmployeesDTO;
	}
	
	public Project createProject(ProjectWithEmployeesIdsDTO dto) {
		
		Project project= new Project();
		project.setName(dto.getProject().getName());
		project.setDescription(dto.getProject().getDescription());
		project.setIs_active(dto.getProject().getIsActive());
		
		Project saveProject=projectRepository.save(project);
		
		for(Long i: dto.getEmployeesIds()) {
			Employee employee = employeeRepository.findById(i).orElseThrow(()-> new RuntimeException("Employee not found with ID: " + i));
			ProjectTeamMember projectTeamMember= new ProjectTeamMember();
			projectTeamMember.setId(new ProjectTeamMemberId(saveProject.getId(), i));
			projectTeamMember.setProject(saveProject);
			projectTeamMember.setEmployee(employee);
			projectTeamMemberRepository.save(projectTeamMember);
		}
		
		return project;
	}
	
	
//	public void createPTM(Long proId, List<Long> empId) {
//		
//	}
	
	public Project updateProject(Long id, ProjectWithEmployeesIdsDTO ptm){
		Project project= projectRepository.findById(id).map(ex->{
			ex.setName(ptm.getProject().getName());
			ex.setDescription(ptm.getProject().getDescription());
			ex.setIs_active(ptm.getProject().getIsActive());
			
			return projectRepository.save(ex);
		}).orElseThrow(()->new RuntimeException("Employee not found!!!!"));
		
		projectTeamMemberRepository.deleteByProjectId(id);
		
		for(Long i: ptm.getEmployeesIds()) {
			Employee employee = employeeRepository.findById(i).orElseThrow(()-> new RuntimeException("Employee not found with ID: " + i));
			ProjectTeamMember projectTeamMember= new ProjectTeamMember();
			projectTeamMember.setId(new ProjectTeamMemberId(id, i));
			projectTeamMember.setProject(project);
			projectTeamMember.setEmployee(employee);
			projectTeamMemberRepository.save(projectTeamMember);
		}
		
		return project;
	}
	
	public void deleteProject(Long id){
//		projectTeamMemberRepository.deleteByProjectId(id);
		projectRepository.deleteById(id);
		
	}
	
}
