import { inject, Service } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginResponse } from '../model/login-response';

@Service()
export class Auth {
    private apiUrl = 'http://localhost:8080/auth';

    private http = inject(HttpClient);

  login(username: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`,
      {
        username,
        password
      }
    );
  }
  register(username: string, password: string) {
    return this.http.post(`${this.apiUrl}/register`,
      {
        username,
        password
      },
      {
        responseType: 'text'
      }
    );
  }
}
