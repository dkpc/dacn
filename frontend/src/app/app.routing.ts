import { LoginComponent } from './views/login/login.component';
import { AdminComponent } from './views/admin/admin.component';
import { StudentComponent } from './views/student/student.component';
import { Routes } from '@angular/router';
import { AuthGuard } from './auth.guard';
export const routes: Routes = [
  { path: '', component: LoginComponent, pathMatch: 'full' },
  { path: 'admin', component: AdminComponent },
  { path: 'student', component: StudentComponent },
];
