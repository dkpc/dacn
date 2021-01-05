import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeResourceUrl, SafeUrl} from '@angular/platform-browser';
@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.css']
})
export class StudentComponent implements OnInit {
  consoleLink: SafeUrl;
  constructor(private sanitizer: DomSanitizer) { 
   }

  ngOnInit(): void {
    const link = localStorage.getItem('console')
    this.consoleLink =
                        this.sanitizer.bypassSecurityTrustResourceUrl
                            (link);
  }

}
