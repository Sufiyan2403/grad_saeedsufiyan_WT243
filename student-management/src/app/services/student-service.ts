import { Injectable } from '@angular/core';
import { Student } from '../models/student';


@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private students: Student[] = [
    { regNo: 'REG001', rollNo: 1, name: 'sufiyan', standard: '12th', school: 'OLPS High School' },
    { regNo: 'REG002', rollNo: 2, name: 'saeed', standard: '9th', school: 'OLPS High School' },
  ];

  getAll(): Student[] {
    return this.students;
  }

  getByRegNo(regNo: string): Student | undefined {
    return this.students.find(s => s.regNo === regNo);
  }

  add(student: Student): void {
    this.students.push(student);
  }

  update(regNo: string, updated: Student): void {
    const index = this.students.findIndex(s => s.regNo === regNo);
    if (index !== -1) {
      this.students[index] = updated;
    }
  }

  delete(regNo: string): void {
    this.students = this.students.filter(s => s.regNo !== regNo);
  }

}