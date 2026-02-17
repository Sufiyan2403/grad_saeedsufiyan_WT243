package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Students {

@Id
@Column(name="rollno")
private int rollNo;
private String name;
private String standard;
private String school;


public int getRollNo() {
	return rollNo;
}
public void setRollNo(int rollNo) {
	this.rollNo = rollNo;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getStandard() {
	return standard;
}
public void setStandard(String standard) {
	this.standard = standard;
}
public String getSchool() {
	return school;
}
public void setSchool(String school) {
	this.school = school;
}


}
