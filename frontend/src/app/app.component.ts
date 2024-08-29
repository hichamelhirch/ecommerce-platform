import {Component, OnInit} from '@angular/core';
import {UserStorageService} from "./services/storage/user-storage.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit{
  title = 'frontend';
  constructor(private router :Router) {
  }
  isAdminLoggedIn:boolean=UserStorageService.isAdminLoggedIn();

  isCustomerLoggedIn:boolean=UserStorageService.isCustomerLoggedIn();

  ngOnInit(): void {
    this.router.events.subscribe(()=>{
      this.isAdminLoggedIn=UserStorageService.isAdminLoggedIn();
      this.isCustomerLoggedIn=UserStorageService.isCustomerLoggedIn();
    })
  }

  logout(){
    UserStorageService.signOut();
    this.router.navigateByUrl('/login');
  }
}
