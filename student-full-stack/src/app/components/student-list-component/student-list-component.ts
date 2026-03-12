import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Student } from '../../model/student';
import { StudentService } from '../../services/student-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-student-list-component',
  standalone: false,
  templateUrl: './student-list-component.html',
  styleUrl: './student-list-component.css',
})

export class StudentListComponent implements OnInit {

  students: Student[] = [];
  filteredStudents: Student[] = [];
  searchTerm: string = '';
  isLoading: boolean = false;

  displayedColumns: string[] = [
    'regno', 'rollno', 'name', 'gender', 'standard', 'school', 'percentage', 'actions'
  ];

  constructor(
    private studentService: StudentService,
    private router: Router,
    private cdr : ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadStudents();
  }

  loadStudents(): void {
    this.isLoading = true;
    this.studentService.getStudents().subscribe({
      next: (data) => {
        this.students = data;
        this.filteredStudents = data;
        this.isLoading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error loading students', err);
        this.isLoading = false;
      }
    });
  }

  applySearch(): void {
    const term = this.searchTerm.toLowerCase();
    this.filteredStudents = this.students.filter(s =>
     {s.name.toLowerCase().includes(term) ||
      s.school.toLowerCase().includes(term) ||
      s.standard.toLowerCase().includes(term) ||
      s.regno.toString().includes(term)
      this.cdr.detectChanges();
     }
    );
  }

  clearSearch(): void {
    this.searchTerm = '';
    this.filteredStudents = this.students;
  }

  addStudent(): void {
    this.router.navigate(['/students/add']);
  }

  editStudent(regno: number): void {
    this.router.navigate(['/students/edit', regno]);
  }

  deleteStudent(regno: number): void {
    if (!confirm(`Are you sure you want to delete student with Regno ${regno}?`)) return;

    this.studentService.deleteStudent(regno).subscribe({
      next: (msg) => {
        alert(msg);
        this.loadStudents();
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Delete failed', err)
    });
  }

  getPassStatus(percentage: number): string {
    return percentage >= 40 ? 'Pass' : 'Fail';
  }
}
