import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ValidationAbsenceComponent } from './validation-absence.component';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from 'src/app/guards/auth.guard';

const routes: Routes =
  [
    { path: 'validation-absences', component: ValidationAbsenceComponent, canActivate: [AuthGuard] }
  ];

@NgModule({
  declarations: [
    ValidationAbsenceComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ]
})
export class ValidationAbsenceModule { }
