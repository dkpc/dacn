import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  constructor(private router: Router, private http: HttpClient,
    private spinner: NgxSpinnerService) { }

    deleteAllVM(){
      this.http.delete(``)
    }
}
