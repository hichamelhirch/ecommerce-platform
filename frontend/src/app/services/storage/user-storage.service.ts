import { Injectable } from '@angular/core';
import {HttpResponse} from "@angular/common/http";


const TOKEN='ecom-toekn';
const USER='ecom-user';
@Injectable({
  providedIn: 'root'
})

export class UserStorageService {

  constructor() { }

  public saveToken(token :string){
   window.localStorage.removeItem(TOKEN);
   window.localStorage.setItem(TOKEN,token);
  }

  public saveUser(user: any){
    window.localStorage.removeItem(USER);
    window.localStorage.setItem(TOKEN,JSON.stringify(user));
  }
  static getToken():string{
    return localStorage.getItem(TOKEN);
  }
  static getUser():any{
    return JSON.parse(localStorage.getItem(USER));
  }
  static getUSerId():string{
    const user=this.getUser();
    if (user==null){
      return '';
    }
    return user.userId;
  }
  static getUSerRole():string{
    const user=this.getUser();
    if (user==null){
      return '';
    }
    return user.role;
  }
   static isAdminLoggedIn():boolean{
    if (this.getToken()==null){
      return false;
    }
    let role=this.getUSerRole();
    return role=='ADMIN';
   }

  static isCustomerLoggedIn():boolean{
    if (this.getToken()==null){
      return false;
    }
    let role=this.getUSerRole();
    return role=='CUSTOMER';
  }
  static signOut(){
    window.localStorage.removeItem(TOKEN);
    window.localStorage.removeItem(USER);
  }
}
