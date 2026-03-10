import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class User {

  constructor(){

  }

  private name:string = "guest"
  private role:string ="";

  public setName(name:string):void{
    this.name=name;
  }

  public getName():string{
    return this.name;
  }

  public setRole(role:string):void{
    this.role=role;
  }

  public getRole():string{
    return this.role;
  }

}
