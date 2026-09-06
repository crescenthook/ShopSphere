import { Component } from '@angular/core';
import { ReactiveFormsModule, FormGroup, Validators,FormControl } from '@angular/forms';
import { Auth } from '../services/auth';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-login',
  styleUrl: './login.css',
  templateUrl: './login.html',
})
export class Login {
  loginForm = new FormGroup(
    {
      username: new FormControl('',[ Validators.required]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)])
    }
  );

  constructor(private authService: Auth) {}
  
  login(){
    
    if (this.loginForm.invalid) {
      return;
    }

    const { username, password } = this.loginForm.value;

    this.authService.login(username!, password!).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.token);
        console.log('Login successful:', response.token);
      },
      error: (error) => {
        console.error('Login failed:', error);
      }
    });
  }
}
