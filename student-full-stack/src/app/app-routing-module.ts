import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard-component/dashboard-component';
import { StudentListComponent } from './components/student-list-component/student-list-component';
import { StudentFormComponent } from './components/student-form-component/student-form-component';
import { StudentFilterComponent } from './components/student-filter-component/student-filter-component';

const routes: Routes = [
  {
    path:'',
    component:DashboardComponent,
    pathMatch:'full'
  },
  {
    path:'dashboard',
    component:DashboardComponent
  },
  {
    path:'students',
    component:StudentListComponent,
  }
  ,
  { path: 'students/add', 
    component: StudentFormComponent 
  },

  { path: 'students/edit/:regno',
     component: StudentFormComponent

  },

  { path: 'filter',
    component: StudentFilterComponent 
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
