import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Login } from './login/login';
import { Admin } from './admin/admin';
import { Staff } from './staff/staff';
import { User } from './services/user';
import { StudentService } from './services/student-service';
import { Home } from './admin/home/home';
import { Insert } from './admin/insert/insert';
import { Update } from './admin/update/update';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, ROUTES } from '@angular/router';

@NgModule({
  declarations: [App, Login, Admin, Staff, Home, Insert, Update],
  imports: [BrowserModule, AppRoutingModule ,CommonModule , FormsModule],
  providers: [provideBrowserGlobalErrorListeners(), User, StudentService],
  bootstrap: [App],
})
export class AppModule {}
