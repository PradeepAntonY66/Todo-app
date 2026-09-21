import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { LoginRequest, LoginResponse } from '../../models/auth';
import { tap } from 'rxjs';

@Service()
export class Auth {
    private http = inject(HttpClient);

    private readonly apiUrl = 'http://localhost:8080/auth';

    login( credentials : LoginRequest) {
        return this.http.post<LoginResponse>(
            `${this.apiUrl}/login`,credentials
        )
        .pipe(
            tap(response => {
                localStorage.setItem("token", response.token);
            })
        )
    }

    logout() {
        localStorage.removeItem("token");
    }

    getToken() : string | null {
        return localStorage.getItem('token');
    }

    isAuthenticated() : boolean {
        return this.getToken() !== null;
    }


}
