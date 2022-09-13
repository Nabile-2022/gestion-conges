import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DemandeAbsenceComponent } from './demande-absence.component';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

const routes: Routes =
[
  { path: 'gestion-absences/demande', component: DemandeAbsenceComponent },
];

@NgModule({
  declarations: [
    DemandeAbsenceComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    RouterModule.forChild(routes)
  ]
})
export class DemandeAbsenceModule { }
