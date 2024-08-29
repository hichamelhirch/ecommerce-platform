import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";
import {AuthService} from "../services/auth/auth.service";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss'
})
export class SignupComponent implements OnInit{
  signupForm!: FormGroup;
  hidePassword = true;

  constructor(private fb :FormBuilder,
              private snackBar: MatSnackBar,
              private authService : AuthService,
              private router : Router) {
  }


  ngOnInit(): void {
    this.signupForm=this.fb.group({
      name :[null,[Validators.required]],
      email :[null,[Validators.required,Validators.email]],
      password :[null,[Validators.required]],
      confirmPassword:[null,[Validators.required]],
    })
  }
  togglePasswordVisibility() {
   this.hidePassword=!this.hidePassword;
  }



  onSubmit() {
    const password = this.signupForm.get('password')?.value;
    const confirmPassword = this.signupForm.get('confirmPassword')?.value;
    if (password!==confirmPassword){
        this.snackBar.open('Password do not match','Close',{duration:5000});
      return;
    }
    this.authService.register(this.signupForm.value).subscribe(
      (response)=>{
        this.snackBar.open('Sign up Successful','Close',{duration :5000});
        this.router.navigateByUrl("/login");
      },
      (error)=>{
        this.snackBar.open('Sign up failed. Please tru again','Close',{duration:5000,panelClass: 'error-snackbar'});
      }
    )
  }

}
