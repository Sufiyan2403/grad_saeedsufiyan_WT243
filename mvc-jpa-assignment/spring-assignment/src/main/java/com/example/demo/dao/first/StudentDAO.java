package com.example.demo.dao.first;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Students;

public interface StudentDAO extends JpaRepository<Students, Integer> {

}
