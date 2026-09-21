import { Component, inject } from '@angular/core';
import { Auth } from '../../Services/auth/auth';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  imports: [FormsModule],
  selector: 'app-login',
  styleUrl: './login.css',
  templateUrl: './login.html',
})
export class Login {
  private authService = inject(Auth);
  private router = inject(Router);

  email = '' ; 
  password = '';

  login() {
    const credentials = {
      email : this.email,
      password : this.password
    };

    this.authService.login(credentials)
      .subscribe({
        next : () => {
          this.router.navigate(['/tasks']);
        },
        error : (error) => {
          console.log('Login failed. ',error);
        }
      })

  }
}
