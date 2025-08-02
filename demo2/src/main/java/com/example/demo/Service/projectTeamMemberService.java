package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.ProjectTeamMember;
import com.example.demo.Repository.ProjectTeamMemberRepository;

@Service
public class projectTeamMemberService {

	@Autowired
	private ProjectTeamMemberRepository projectTeamMemberRepository;
	
	public List<ProjectTeamMember> getAllProjectTeamMember(){
		
		return projectTeamMemberRepository.findAll();
	}
	
	public ProjectTeamMember createProjectTeamMember(ProjectTeamMember ptm){
		return projectTeamMemberRepository.save(ptm);
	}
	
	public void deleteProjectTeamMember(Long ptmId){
		
		projectTeamMemberRepository.deleteByProjectId(ptmId);
	}
	
}
