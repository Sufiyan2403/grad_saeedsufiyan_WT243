import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { HttpClientModule } from '@angular/common/http';
import { StudentService } from './services/student-service';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard-component/dashboard-component';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { FormsModule } from '@angular/forms';
import { MatProgressSpinnerModule, MatSpinner } from '@angular/material/progress-spinner';
import { StudentListComponent } from './components/student-list-component/student-list-component';
import { MatTableModule } from '@angular/material/table';
import { StudentFormComponent } from './components/student-form-component/student-form-component';
import { MatDividerModule } from '@angular/material/divider';
import { StudentFilterComponent } from './components/student-filter-component/student-filter-component';

@NgModule({
  declarations: [
    App,
    DashboardComponent,
    StudentListComponent,
    StudentFormComponent,
    StudentFilterComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    RouterModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    FormsModule,
    MatProgressSpinnerModule,
    MatTableModule,
    MatDividerModule,
  ],
  providers: [provideBrowserGlobalErrorListeners(), StudentService],
  bootstrap: [App],
})
export class AppModule {}
