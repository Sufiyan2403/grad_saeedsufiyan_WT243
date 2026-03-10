import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Login } from './login/login';
import { Admin } from './admin/admin';
import { Staff } from './staff/staff';
import { adminGuard } from './guards/admin-guard';
import { Home } from './admin/home/home';
import { Insert } from './admin/insert/insert';
import { Update } from './admin/update/update';
import { authGuardGuard } from './guards/auth-guard-guard';

const routes: Routes = [
{
  path: '',
  redirectTo : 'login',
  pathMatch : 'full'
},
{
  path: 'login',
  component : Login
},
{
  path : 'admin',
  component: Admin,
  canActivate:[adminGuard],
  children: [
      { path: 'home', component: Home },
      { path: 'insert', component: Insert },
      { path: 'update/:regNo', component: Update },
      { path: '', redirectTo: 'home', pathMatch: 'full' }
    ]
},
{
  path : 'staff',
  component :Staff,
  canActivate:[authGuardGuard]
}


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
