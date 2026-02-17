package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.StudentDao;
import com.example.demo.models.Student;

@RestController
public class StudentRestController {

	@Autowired
	private StudentDao dao;
	
	@GetMapping("/students")
	public List<Student> getStudents()
	{
		return dao.findAll();
	}
	
	
	@GetMapping("/students/{regno}")
	public Optional<Student> getbyNo(@PathVariable int regno){
		
		return dao.findById(regno);
	}
	
	@PostMapping("/students")
	public String insertStudent(@RequestBody Student s)
	{
		dao.save(s);
		return "inserted Sucessfully";
	}
	
	@PutMapping("/students/{regno}")
	public String updateStudent(@PathVariable int regno , @RequestBody Student s)
	{
		if(!dao.existsById(regno))
			return "Record does not exist";
		else
		{
			dao.save(s);
			return "successfully updated";
		}
			
	}
	
	@PatchMapping("/students/{regno}")
	public String updatePartial(@PathVariable int regno,@RequestBody Student s){
		Optional<Student> o1 = dao.findById(regno);
		Student s1 = o1.get();
		if(s.getRegno()==0) {
			s.setRegno(s1.getRegno());
		}
		if(s.getRollno()==0)
		{
			s.setRollno(s1.getRollno());
		}
		if(s.getName()==null) {
			s.setName(s1.getName());
		}
		if(s.getGender()==null) {
			s.setGender(s1.getGender());
		}
		if(s.getStandard() == null) {
			s.setStandard(s1.getStandard());
		}
		if(s.getSchool() == null) {
			s.setSchool(s1.getSchool());
		}
		
		if(s.getPercentage()==0)
			s.setPercentage(s1.getPercentage());
		
		dao.save(s);
		return "Successfully Updated";
	}
	
	@DeleteMapping("/students/{regno}")
	public String deleteStudent(@PathVariable int regno)
	{
		if(!dao.existsById(regno))
			return "No record exists";
		
		dao.deleteById(regno);
		return "Succesfully deleted";
	}
	
	@GetMapping("/students/school")
	public List<Student> bySchool(@RequestParam String name){
		
		return dao.findBySchool(name);
	}
	
	@GetMapping("/students/school/count")
	public int countSchool(@RequestParam String school) {
		
		return dao.countBySchool(school);
	}
	
	@GetMapping("/students/school/standard/count")
	public int countStandard(@RequestParam String standard) {
		
		return dao.countByStandard(standard);
	}
	
	@GetMapping("/students/result")
	public List<Student> getResults(@RequestParam boolean pass){
		
		if(pass)
			return dao.findByPercentageGreaterThanEqualOrderByPercentageDesc(40.0);
		else
			return dao.findByPercentageLessThanOrderByPercentageDesc(40.0);
	}
	
	@GetMapping("/students/strength")
	public int countStrength(@RequestParam String gender , @RequestParam String standard) {
	
	return dao.countByGenderAndStandard(gender, standard);
	
	}
	
}
