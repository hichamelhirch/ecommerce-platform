import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";
import {AuthService} from "../services/auth/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit{
  loginForm: FormGroup
  hidePassword= true;
   constructor(private fb:FormBuilder,
               private snackBar :MatSnackBar,
               private router :Router,
               private authService:AuthService) {
   }
  ngOnInit(): void {
    this.loginForm=this.fb.group({

      email :[null,[Validators.required,Validators.email]],
      password :[null,[Validators.required]]
    })
  }

  togglePasswordVisibility() {
    this.hidePassword=!this.hidePassword
  }
  onSubmit() {
    const username = this.loginForm.get('email')?.value;
    const password = this.loginForm.get('password')?.value;

    this.authService.login(username,password).subscribe(
      (response)=>{
        this.snackBar.open('Login Successful','OK',{duration :5000});
        this.router.navigateByUrl('/signup')
      },
      (error)=>{
        this.snackBar.open('BAd Credentials','ERROR',{duration:5000});
      }
    )
  }



}
