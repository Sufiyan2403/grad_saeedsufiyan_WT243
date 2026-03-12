import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { StudentService } from '../../../services/student-service';

@Component({
  selector: 'app-dashboard-component',
  standalone: false,
  templateUrl: './dashboard-component.html',
  styleUrl: './dashboard-component.css',
})

export class DashboardComponent implements OnInit {

  totalStudents: number = 0;
  totalPassed: number = 0;
  totalFailed: number = 0;

  // Strength query
  strengthResult: number | null = null;
  strengthGender: string = '';
  strengthStandard: string = '';

  // School count query
  schoolCountResult: number | null = null;
  schoolCountName: string = '';

  // Standard count query
  standardCountResult: number | null = null;
  standardCountName: string = '';

  constructor(private studentService: StudentService , private cdr : ChangeDetectorRef) {}

  ngOnInit(): void {
    this.loadSummary();
  }

  loadSummary(): void {
    this.studentService.getStudents().subscribe(students => {
      this.totalStudents = students.length;
      this.cdr.detectChanges();
    });

    this.studentService.getResults(true).subscribe(passed => {
      this.totalPassed = passed.length;
      this.cdr.detectChanges();
    });

    this.studentService.getResults(false).subscribe(failed => {
      this.totalFailed = failed.length;
      this.cdr.detectChanges();
    });
  }

  fetchStrength(): void {
    if (!this.strengthGender || !this.strengthStandard) return;
    this.studentService.countStrength(this.strengthGender, this.strengthStandard)
      .subscribe(count => {this.strengthResult = count ; this.cdr.detectChanges()  });
  }

  fetchSchoolCount(): void {
    if (!this.schoolCountName) return;
    this.studentService.countBySchool(this.schoolCountName)
      .subscribe(count => {this.schoolCountResult = count ; this.cdr.detectChanges()});
    this.cdr.detectChanges();
  }

  fetchStandardCount(): void {
    if (!this.standardCountName) return;
    this.studentService.countByStandard(this.standardCountName)
      .subscribe(count => {this.standardCountResult = count; this.cdr.detectChanges()});
    this.cdr.detectChanges();
  }
}