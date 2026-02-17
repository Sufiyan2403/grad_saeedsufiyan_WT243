package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Student;

public interface StudentDao extends JpaRepository<Student, Integer> {

	public List<Student> findBySchool(String name);
	
	public int countBySchool(String name);
	
	public int countByStandard(String std);
	
	public List<Student> findByPercentageGreaterThanEqualOrderByPercentageDesc(double percentage);
	
	public List<Student> findByPercentageLessThanOrderByPercentageDesc(double percentage);
	
	public int countByGenderAndStandard(String gender , String standard);
}
