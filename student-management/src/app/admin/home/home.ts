import { Component, OnInit } from '@angular/core';
import { Student } from '../../models/student';
import { StudentService } from '../../services/student-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: false,
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home implements OnInit{

students: Student[] = [];

  constructor(private studentService: StudentService, private router: Router) {}

  ngOnInit(): void {
    this.students = this.studentService.getAll();
  }

  delete(regNo: string): void {
    this.studentService.delete(regNo);
    this.students = this.studentService.getAll();
  }

  goToUpdate(regNo: string): void {
    this.router.navigate(['/admin/update', regNo]);
  }


}
