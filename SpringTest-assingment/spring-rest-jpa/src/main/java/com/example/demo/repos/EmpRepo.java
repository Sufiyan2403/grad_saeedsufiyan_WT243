package com.example.demo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Employee;

public interface EmpRepo extends JpaRepository<Employee, Integer> {

	public List<Employee> findByDesignation(String designation);
}
