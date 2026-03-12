import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Student } from '../../model/student';
import { StudentService } from '../../services/student-service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-student-form-component',
  standalone: false,
  templateUrl: './student-form-component.html',
  styleUrl: './student-form-component.css',
})

export class StudentFormComponent implements OnInit {

  isEditMode: boolean = false;
  isLoading: boolean = false;
  isSaving: boolean = false;

  student: Student = {
    regno: 0,
    rollno: 0,
    name: '',
    gender: '',
    standard: '',
    school: '',
    percentage: 0
  };

  constructor(
    private studentService: StudentService,
    private route: ActivatedRoute,
    private router: Router,
    private cdr : ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const regno = this.route.snapshot.paramMap.get('regno');
    if (regno) {
      this.isEditMode = true;
      this.isLoading = true;
      this.studentService.getById(Number(regno)).subscribe({
        next: (data) => {
          this.student = data;
          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: (err) => {
          console.error('Error loading student', err);
          this.isLoading = false;
        }
      });
    }
  }

  onSubmit(): void {
    if (this.isSaving) return;
    this.isSaving = true;

    if (this.isEditMode) {
      this.studentService.updateStudent(this.student.regno, this.student).subscribe({
        next: (msg) => {
          alert(msg);
          this.isSaving = false;
          this.router.navigate(['/students']);
         this.cdr.detectChanges();

        },
        error: (err) => {
          console.error('Update failed', err);
          this.isSaving = false;
        }
      });
    } else {
      this.studentService.insertStudent(this.student).subscribe({
        next: (msg) => {
          alert(msg);
          this.isSaving = false;
          this.router.navigate(['/students']);
        },
        error: (err) => {
          console.error('Insert failed', err);
          this.isSaving = false;
        }
      });
    }
  }

  onCancel(): void {
    this.router.navigate(['/students']);
  }
}