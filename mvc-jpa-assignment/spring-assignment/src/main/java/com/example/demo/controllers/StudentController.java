package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.StudentService;
import com.example.demo.dao.first.StudentDAO;
import com.example.demo.models.Students;

@Controller
public class StudentController {
	
	@Autowired
	private StudentService service;

	@RequestMapping("/")
	public String homePage() {
		
		return "student.html";
	}
	
	@RequestMapping("/insert")
	@ResponseBody
	public String insertStudent( Students s) {
		
		String message =  service.save(s);
		return message;
		
		
	}
	
}
