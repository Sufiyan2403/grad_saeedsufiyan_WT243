import { Component, OnInit } from '@angular/core';
import { Student } from '../../models/student';
import { ActivatedRoute, Router } from '@angular/router';
import { StudentService } from '../../services/student-service';

@Component({
  selector: 'app-update',
  standalone: false,
  templateUrl: './update.html',
  styleUrl: './update.css',
})

export class Update implements OnInit {

  regNo: string = '';
  student: Student = {
    regNo: '',
    rollNo: 0,
    name: '',
    standard: '',
    school: ''
  };

  constructor(
    private route: ActivatedRoute,
    private studentService: StudentService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.regNo = this.route.snapshot.paramMap.get('regNo') || '';
    const found = this.studentService.getByRegNo(this.regNo);
    if (found) {
      this.student = { ...found };
    } else {
      alert('Student not found!');
      this.router.navigate(['/admin/home']);
    }
  }

  onSubmit(): void {
    this.studentService.update(this.regNo, this.student);
    alert('Student updated successfully!');
    this.router.navigate(['/admin/home']);
  }

  goBack(): void {
    this.router.navigate(['/admin/home']);
  }

}
