import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from 'src/app/auth.service';
import { LoginRes } from 'src/app/model/LoginRes';
import { UploadDialogComponent } from 'src/app/views/upload-dialog/upload-dialog.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  username: string;
  role: boolean;
  constructor(  private authService: AuthService,
     private dialog: MatDialog) {}

  ngOnInit(): void {
    this.username = localStorage.getItem('username');
    this.role = localStorage.getItem('role') == 'admin'? true : false;
  }
  openDialog(){
    this.dialog.open( UploadDialogComponent, {
      width: '60%',
      height: '80%',
    });
  }

  logout(){
    this.authService.logout();
  }
}
