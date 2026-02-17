package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.first.StudentDAO;
import com.example.demo.dao.second.StudentSecondDAO;
import com.example.demo.models.Students;

@Service
public class StudentService {

	@Autowired
	private StudentDAO dao1;
	
	@Autowired
	private StudentSecondDAO dao2;
	
	public String save(Students student) {
		
		String message;
		
		if(dao1.existsById(student.getRollNo()) && dao2.existsById(student.getRollNo()))
			return "roll no. already exisits";
		else {
			dao1.save(student);
			dao2.save(student);
			return "successfully added";
		}
		
		
	}
	
}
