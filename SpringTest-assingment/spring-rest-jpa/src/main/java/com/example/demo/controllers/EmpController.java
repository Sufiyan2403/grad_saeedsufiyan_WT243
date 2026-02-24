package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Employee;
import com.example.demo.repos.EmpRepo;

@RestController // for rest api controller , no need for response body in rest controler as what we return in rest controller is by default a resource
public class EmpController {
	@Autowired
	EmpRepo repo;
	

	
	@GetMapping("/greet")
	public String greet() {
		
		return "<h2>Good morning Everyone </h2>";
	}
	
	@GetMapping("/")
	public String abc() {
		return "<h2>Welcome to REST API Demo</h2>";
	}
	
	@GetMapping(path="/employee", produces="application/xml") //content negotiation
	public List<Employee> getEmployee(){
		
		return repo.findAll();
	}
	
	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> insertEmployee(@PathVariable int id){
		
		Optional<Employee> emp = repo.findById(id);
		
		if(emp.isPresent())
			return ResponseEntity.ok(emp.get());
		else
			return ResponseEntity.status(204).build();	}
	
	@PostMapping("/employee")
	public String getEmployees(@RequestBody Employee e)
	{
		if(repo.existsById(e.getEid()))
			return "Employee record already exists";
		
		repo.save(e);
		return "Successfully added employee record";
	}
	
	@PutMapping("/employee/{id}")
	public String updateEmployees(@PathVariable int  id , @RequestBody Employee e)
	{
		if(e.getEid()!=id)
			return "Employee ids do not match";
		
		if(!repo.existsById(id))
			return "No such employee exisits";
		
		repo.save(e);
		return "Successfully updated employee record";
	}
	
	@DeleteMapping("/employee/{id}")
	public String removeEmployee(@PathVariable int id) {
		if(repo.existsById(id)) {
			repo.deleteById(id);
			return "employee deleted successfully";
		}
		
		return "no record found to delete";
	
	}
	
	
	//---------CUstom functions------------------
	@GetMapping("/employee/role")
	public List<Employee> retrieveBaseddesignation(@RequestParam String  designation) // without explicityly mentioning that it is a query parameter , bydefault it is a query parameter
	{
		return repo.findByDesignation(designation);
	}
	
	
	
	
}
