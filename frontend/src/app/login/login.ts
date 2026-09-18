import { Component, signal } from '@angular/core';
import { ReactiveFormsModule, FormGroup, Validators,FormControl } from '@angular/forms';
import { Auth } from '../services/auth';
import { RouterLink } from '@angular/router';

@Component({
  imports: [ReactiveFormsModule, RouterLink],
  selector: 'app-login',
  styleUrl: './login.css',
  templateUrl: './login.html',
})
export class Login {

  message = signal<String>('');
  messageType = signal<'success' | 'error'>('success');

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
        this.message.set('Login successful!');
        this.messageType.set('success');
        localStorage.setItem('token', response.token);
        console.log('Login successful:', response.token);
      },
      error: (error) => {
        this.message.set('Invalid username or password.');
        this.messageType.set('error');
        console.error('Login failed:', error);
      }
    });
  }
}
