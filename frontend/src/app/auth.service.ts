import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { HttpClient, HttpParams, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import { LoginRes } from 'src/app/model/LoginRes';
import { NgxSpinnerService } from 'ngx-spinner';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private router: Router, private http: HttpClient,
    private spinner: NgxSpinnerService) {}

  public isLoggin() {
    return localStorage.getItem('data') == null ? false : true;
  }

  public login(username: string, password: string) {
    const params = new HttpParams()
      .set('password', password)
      .set('username', username);
    console.log(params);
    const headers = {
      accept: '*/*',
    };
    console.log(headers);
    return this.http
      .post<LoginRes>(
        `http://localhost:9191/user/login/?password=` + password + `&username=` + username,
        {
          headers: headers,
        }
      )
      .pipe(
        map((data) => {
          localStorage.setItem('username', data.user.name);
          localStorage.setItem('role', data.user.role);
          localStorage.setItem('console', data.console);
          data.user.role == 'admin'
            ? this.router.navigate(['/admin'])
            : this.router.navigate(['/student']);
          console.log(data);

          return data;
        }),
        catchError((err) => {
          console.error(err);
          this.spinner.hide();
          alert('Error Server to : ' + err.message);
          throw err;
        })
      );
  }

  createVM(userdata): Observable<any> {
    return this.http.put('http://localhost:9191/submit/submit', userdata).pipe(catchError(this.handleError));
  }
  handleError(error: HttpErrorResponse) {
    return throwError(error);
  }

  public logout() {
    localStorage.clear;
    this.router.navigate(['']);
  }
}
