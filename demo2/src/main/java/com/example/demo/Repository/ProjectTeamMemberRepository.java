package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.ProjectTeamMember;
import com.example.demo.Entity.Embedded.ProjectTeamMemberId;

import jakarta.transaction.Transactional;

@Repository
public interface ProjectTeamMemberRepository extends JpaRepository<ProjectTeamMember, ProjectTeamMemberId> {
	

    List<ProjectTeamMember> findById_ProjectId(Long project_id);

    List<ProjectTeamMember> findById_EmployeeId(Long employee_id);
	
    @Modifying
    @Transactional
    @Query("DELETE FROM ProjectTeamMember ptm WHERE ptm.id.projectId = :projectId")
    void deleteByProjectId(@Param("projectId") Long projectId);
    
}
