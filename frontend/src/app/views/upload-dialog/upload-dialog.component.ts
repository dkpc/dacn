import { HttpClient } from '@angular/common/http';
import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { FileSystemDirectoryEntry, FileSystemFileEntry, NgxFileDropEntry } from 'ngx-file-drop';
import { NgxSpinnerService } from 'ngx-spinner';
import { catchError } from 'rxjs/operators';
import { TestRes } from 'src/app/model/TestRes';

@Component({
  selector: 'app-upload-dialog',
  templateUrl: './upload-dialog.component.html',
  styleUrls: ['./upload-dialog.component.css']
})
export class UploadDialogComponent implements OnInit {
  upload_table: any;
  stdName: string;
  score: number;
  status: string;
  stdId: number;

  @ViewChild('secondDialog') secondDialog: TemplateRef<any>;
  constructor(  private spinner: NgxSpinnerService,
    private http: HttpClient,
    private dialogRef: MatDialog) { }
  infoSubmitForm = new FormGroup({
    classname: new FormControl('', [
      Validators.required,
    ]),
    examId: new FormControl('', [
      Validators.required,
    ]),
    machineId: new FormControl('', [
      Validators.required,
    ]),
    studentId: new FormControl('', [
      Validators.required,
    ])
  });
  public files: NgxFileDropEntry[] = [];
  public dropped(files: NgxFileDropEntry[]) {
    for (const droppedFile of files) {
      // Is it a file?
      if (droppedFile.fileEntry.isFile) {
        const fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
        fileEntry.file((file: File) => {
          console.log(file.name.split('.')[1])
          if (['txt'].includes(file.name.split('.')[1])) {
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
  ngOnInit(): void {
    this.stdName = localStorage.getItem('username');
  }
  public submit(){
    console.log(typeof this.infoSubmitForm.value.classname);
    console.log(typeof this.infoSubmitForm.value.examId);
    console.log(typeof this.infoSubmitForm.value.machineId);
    console.log(typeof this.infoSubmitForm.value.studentId);
    const classnameRequired = this.infoSubmitForm.value.classname === '' ? true : false;
    const examIdRequired = this.infoSubmitForm.value.examId === '' ? true : false;
    const machineIdRequired = this.infoSubmitForm.value.machineId === '' ? true : false;
    const studentIdRequired = this.infoSubmitForm.value.studentId === '' ? true : false;
    console.log(classnameRequired );
    console.log(examIdRequired );
    console.log(machineIdRequired);
    console.log(studentIdRequired );
    if(classnameRequired || examIdRequired || machineIdRequired || studentIdRequired ){
      console.log('hello2222');
      return;
    }
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
          formData.set('classname',  this.infoSubmitForm.value.classname)
          formData.set('examId', this.infoSubmitForm.value.examId)
          formData.set('file',file, droppedFile.relativePath)
          formData.set('machineId', this.infoSubmitForm.value.machineId)
          formData.set('studentId', this.infoSubmitForm.value.studentId)
          this.http.put<TestRes>('http://localhost:9191/submit/submit', formData)
          .subscribe((data => {
            this.spinner.hide();
            // this.dialogRef.closeAll();
            this.status = data.body.mark > 8? 'ĐẠT' : 'TRƯỢT';
            this.score = data.body.mark;
            this.stdId = data.body.studentId;

            this.dialogRef.open(this.secondDialog)
            console.log(data.body.mark);
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
close(){
  this.dialogRef.closeAll();
}
}
