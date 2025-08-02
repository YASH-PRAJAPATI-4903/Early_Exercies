package com.example.demo.Entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Table(name="department")
public class Department {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(length = 50)
    private String name;
	
	@Column(length = 50)
    private String description;
    
	@CreationTimestamp
	@Column(updatable = false)
    private LocalDateTime created_time;
	
	@UpdateTimestamp
    private LocalDateTime updated_time;
	
	@OneToMany(mappedBy = "department", cascade = CascadeType.MERGE)
	private List<Employee> employee;
	
}
