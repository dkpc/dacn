import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FileSystemDirectoryEntry, FileSystemFileEntry, NgxFileDropEntry } from 'ngx-file-drop';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';
import { AccountInfoRes } from 'src/app/model/AccountInfoRes';
import { VMListInfoRes } from 'src/app/model/VMListInfoRes';
import { FormControl, Validators } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { catchError } from 'rxjs/operators';
import { CommonService } from '../../../app/shared/services/common.service'
@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit, AfterViewInit {
  upload_table: any;
  displayedStudentColumns: string[] = ['id', 'student_id', 'name', 'machine_user'];
  displayedVMColumns: string[] = ['id', 'vm_code', 'student_count'];
  dataSource: any;
  VMcount: number = 0;
  vmList:any;
  deleteVMstate: string = '';
  deleteVMstatus: boolean = false
  iconState: string;
  successState: any;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  constructor( private http: HttpClient,
               private spinner: NgxSpinnerService,
               private service: CommonService) {
                }
  classnameFormControl = new FormControl('', [
    Validators.required,
  ]);
  ngAfterViewInit(): void {

  }

  ngOnInit(): void {
    this.upload_table = document.getElementById("upload-table");
    this.upload_table.style.display = "none";
    this.getVMlist()
    this.successState = document.getElementById("success-state");
    this.successState.classList.add('hidden')
  }

  public files: NgxFileDropEntry[] = [];
  public dropped(files: NgxFileDropEntry[]) {
    for (const droppedFile of files) {
      // Is it a file?
      if (droppedFile.fileEntry.isFile) {
        const fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
        fileEntry.file((file: File) => {
          if (['.xls', '.xltx', '.xlt', 'xlsx'].includes(file.name.split('.')[1])) {
            this.files = files;
            this.upload_table.style.display = "block";
          } else {
            alert("định dạng file không hợp lệ")
            return;
          } 
        });
      } else {
        const fileEntry = droppedFile.fileEntry as FileSystemDirectoryEntry;
        console.log(droppedFile.relativePath, fileEntry);
      }
    }
  }
 
  public deleteDoc(docIndex: number) {
    this.files.splice(docIndex, 1);
    this.upload_table.style.display = "none";
  }
  
  public fileOver(event){
    console.log(event);
  }
 
  public fileLeave(event){
    console.log(event);
  }
  
  public submit(){
    this.spinner.show();
    for (const droppedFile of this.files) {
      // Is it a file?
      if (droppedFile.fileEntry.isFile) {
        const fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
        fileEntry.file((file: File) => {
          console.log(droppedFile.relativePath, file);
          const headers = {'Content-Type': 'multipart/form-data'};
          console.log(headers)
          let formData = new FormData();
          formData.set('classname', this.classnameFormControl.value)
          formData.set('examId', '1')
          formData.set('file', file, droppedFile.relativePath)
          formData.set('machineId', '1')
          formData.set('studentId', '1')
          this.http.put<AccountInfoRes[]>('http://localhost:9191/submit/submit', formData)
          .subscribe((data => {
            this.spinner.hide();
            this.spinner.hide();
            this.deleteVMstatus = true;
            this.deleteVMstate = 'Tạo máy ảo thành công'
            this.successState.classList.remove('hidden')
            this.iconState = 'done'
            this.messColor();
            this.deleteDoc(1);
            setTimeout(() => {
              this.successState.classList.add('hidden')
            }, 2000);
            this.getVMlist();
            console.log(data);
            this.dataSource = new MatTableDataSource(data);
            this.dataSource.paginator = this.paginator;
          }),err => {alert(err)
            this.spinner.hide();
          })
        });
      } else {
        // It was a directory (empty directories are added, otherwise only files)
        const fileEntry = droppedFile.fileEntry as FileSystemDirectoryEntry;
        console.log(droppedFile.relativePath, fileEntry);
      }
    }
  }

  public delete(){
    this.spinner.show();
    this.http.delete('http://localhost:9191/machine/deleteAll')
    .subscribe((data => {
      this.spinner.hide();
      this.deleteVMstatus = true;
      this.deleteVMstate = 'Xóa máy ảo thành công'
      this.successState.classList.remove('hidden')
      this.iconState = 'done'
      this.messColor();
      this.getVMlist();
      setTimeout(() => {
        this.successState.classList.add('hidden')
      }, 2500);
    }),err => {alert(err)
      this.spinner.hide();
    })
  }

  public getVMlist(){
    this.http.get<VMListInfoRes[]>('http://localhost:9191/machine/getAllMachine')
    .subscribe((data => {
      console.log(data)
      this.vmList = new MatTableDataSource(data);
      this.VMcount = data.length;
    }),err => {alert(err)
      this.spinner.hide();
    })
  }

  messColor(){
     return this.deleteVMstatus? {'background-color':'#22bb33'}:{'background-color':'#bb2124'}
  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }
}
