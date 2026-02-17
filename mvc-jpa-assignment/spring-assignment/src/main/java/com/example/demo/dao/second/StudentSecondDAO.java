package com.example.demo.dao.second;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Students;

public interface StudentSecondDAO extends JpaRepository<Students, Integer> {

}
