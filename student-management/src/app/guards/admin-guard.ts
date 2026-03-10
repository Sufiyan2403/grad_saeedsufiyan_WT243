import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { User } from '../services/user';

export const adminGuard: CanActivateFn = (route, state) => {
  const us:User = inject(User);
  const router:Router = inject(Router);

  if (us.getRole() === 'Admin') {
    return true;
  }
  router.navigate(['login']);
  return false;

  
};
