import { Component } from '@angular/core';
import { StudentService } from '../../services/student-service';
import { Router } from '@angular/router';
import { Student } from '../../models/student';

@Component({
  selector: 'app-insert',
  standalone: false,
  templateUrl: './insert.html',
  styleUrl: './insert.css',
})
export class Insert {

student: Student = {
    regNo: '',
    rollNo: 0,
    name: '',
    standard: '',
    school: ''
  };

  constructor(private studentService: StudentService, private router: Router) {}

  onSubmit(): void {
    this.studentService.add(this.student);
    alert('Student added successfully!');
    this.router.navigate(['/admin/home']);
  }

  goBack(): void {
  this.router.navigate(['/admin/home']);
}

}
