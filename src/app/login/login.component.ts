import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MessageService } from '../message.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { group } from '@angular/animations';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private authService: AuthService, private route: Router, private messageService: MessageService, private fb: FormBuilder) { }
  ngOnInit(): void {
  }
  loginForm = this.fb.group({
    emailId: ['', [Validators.required, Validators.pattern('^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$')]],
    password: ['', [Validators.required, Validators.pattern(/^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/)]],
  });

  get emailId() {
    return this.loginForm.get("emailId")
  }
  get password() {
    return this.loginForm.get("password")
  }
  responseData: any;
  sendLoginData() {
    this.authService.loginCheck(this.loginForm.value).subscribe({
      next: (response: any) => {
        this.responseData = response;
        console.log('token :' + this.responseData.token);
        localStorage.setItem('jwt', this.responseData.token);
        console.log("email from jwt"+this.responseData.token.emailId);

        this.authService.isLoggedIn = true;
        this.messageService.provideMessages('logged in successfully', "success");
        this.route.navigateByUrl("/home");
      },
      error: (error) => {
        this.messageService.provideMessages('User name and password not matched', 'mismatch');
      }
    });
  }
}



