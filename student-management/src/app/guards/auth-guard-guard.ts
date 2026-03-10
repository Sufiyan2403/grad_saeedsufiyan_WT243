import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { User } from '../services/user';

export const authGuardGuard: CanActivateFn = (route, state) => {
  const us = inject(User);
  const router = inject(Router);

if(us.getRole()!= "")
  return true;
else
  return false;

};
