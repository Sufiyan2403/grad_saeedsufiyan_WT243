import { ChangeDetectorRef, Component } from '@angular/core';
import { Student } from '../../model/student';
import { StudentService } from '../../services/student-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-student-filter-component',
  standalone: false,
  templateUrl: './student-filter-component.html',
  styleUrl: './student-filter-component.css',
})

export class StudentFilterComponent {

  // Results
  students: Student[] = [];
  isLoading: boolean = false;
  hasSearched: boolean = false;
  
activeFilter: 'school' | 'result' = 'school';

  schoolName: string = '';

  passFilter: boolean = true;

  displayedColumns: string[] = [
    'regno', 'rollno', 'name', 'gender', 'standard', 'school', 'percentage'
  ];

  constructor(private studentService: StudentService , private cdr : ChangeDetectorRef) {}

  switchFilter(filter: 'school' | 'result'): void {
    this.activeFilter = filter;
    this.students = [];
    this.hasSearched = false;
  }

  fetchBySchool(): void {
    if (!this.schoolName) return;
    this.isLoading = true;
    this.hasSearched = false;
    this.students = [];
    this.studentService.getBySchool(this.schoolName).subscribe({
      next: (data) => {
        this.students = data;
        this.isLoading = false;
        this.hasSearched = true;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error fetching by school', err);
        this.isLoading = false;
        this.hasSearched = true;
      }
    });
  }

  fetchByResult(): void {
    this.isLoading = true;
    this.hasSearched = false;
    this.students = [];
    this.studentService.getResults(this.passFilter).subscribe({
      next: (data) => {
        this.students = data;
        this.isLoading = false;
        this.hasSearched = true;
        this.cdr.detectChanges();

      },
      error: (err) => {
        console.error('Error fetching results', err);
        this.isLoading = false;
        this.hasSearched = true;
      }
    });
  }

  getPassStatus(percentage: number): string {
    return percentage >= 40 ? 'Pass' : 'Fail';
  }
}