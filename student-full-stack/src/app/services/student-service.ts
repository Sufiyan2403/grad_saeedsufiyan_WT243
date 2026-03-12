import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Student } from '../model/student';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class StudentService {

private baseUrl = 'http://localhost:8282';

  constructor(private http: HttpClient) {}

  getStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(`${this.baseUrl}/students`);
  }

  getById(regno: number): Observable<Student> {
    return this.http.get<Student>(`${this.baseUrl}/students/${regno}`);
  }

  insertStudent(student: Student): Observable<string> {
    return this.http.post(`${this.baseUrl}/students`, student, { responseType: 'text' });
  }

  updateStudent(regno: number, student: Student): Observable<string> {
    return this.http.put(`${this.baseUrl}/students/${regno}`, student, { responseType: 'text' });
  }

  patchStudent(regno: number, partial: Partial<Student>): Observable<string> {
    return this.http.patch(`${this.baseUrl}/students/${regno}`, partial, { responseType: 'text' });
  }

  deleteStudent(regno: number): Observable<string> {
    return this.http.delete(`${this.baseUrl}/students/${regno}`, { responseType: 'text' });
  }

  getBySchool(name: string): Observable<Student[]> {
    const params = new HttpParams().set('name', name);
    return this.http.get<Student[]>(`${this.baseUrl}/students/school`, { params });
  }

  countBySchool(school: string): Observable<number> {
    const params = new HttpParams().set('school', school);
    return this.http.get<number>(`${this.baseUrl}/students/school/count`, { params });
  }

  countByStandard(standard: string): Observable<number> {
    const params = new HttpParams().set('standard', standard);
    return this.http.get<number>(`${this.baseUrl}/students/school/standard/count`, { params });
  }

  getResults(pass: boolean): Observable<Student[]> {
    const params = new HttpParams().set('pass', String(pass));
    return this.http.get<Student[]>(`${this.baseUrl}/students/result`, { params });
  }

  countStrength(gender: string, standard: string): Observable<number> {
    const params = new HttpParams().set('gender', gender).set('standard', standard);
    return this.http.get<number>(`${this.baseUrl}/students/strength`, { params });
  }




}
