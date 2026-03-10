import { Component } from '@angular/core';
import { Route, Router } from '@angular/router';
import { User } from '../services/user';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  constructor(private router:Router , private us:User){

  }

public userLogin(event:any):void{

  event.preventDefault();
  let uname:string = event.target.elements[0].value;
  let password:string = event.target.elements[1].value;
  let role:string = event.target.elements[2].value;

  if(uname ==  password && role == "Admin"){

      this.us.setName(uname);
      this.us.setRole(role);

      this.router.navigate(['admin']);

  }
  
  else if(uname ==  password && role=="Staff")
  {
    this.us.setName(uname);
    this.us.setRole(role);
    this.router.navigate(['staff'])
   
  }

  else{
    this.router.navigate(['failure'])
  }

}


}
