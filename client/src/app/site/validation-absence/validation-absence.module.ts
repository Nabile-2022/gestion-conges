import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ValidationAbsenceComponent } from './validation-absence.component';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes =
[
  { path: 'validation-absences', component: ValidationAbsenceComponent }
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
