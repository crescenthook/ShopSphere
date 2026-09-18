import { Component,signal,inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Auth } from '../services/auth';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-register',
  styleUrl: './register.css',
  templateUrl: './register.html',
})
export class Register {

  private authService = inject(Auth);

  message = signal<string>('');

  registerForm = new FormGroup(
    {
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    }
  );

  onSubmit(): void {
  if (this.registerForm.invalid) {
    this.registerForm.markAllAsTouched();
    return;
  }
    const username = this.registerForm.controls.username.value;
    const password = this.registerForm.controls.password.value;
    this.authService.register(username!,password!).subscribe({
      next: (response) => {this.message.set(response);},
      error: (error) => {console.log('Registration failed:', error);}
    });
  }
}
