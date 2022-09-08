import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DemandeAbsenceComponent } from './demande-absence.component';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes =
[
  { path: 'demande-absence', component: DemandeAbsenceComponent },
];

@NgModule({
  declarations: [
    DemandeAbsenceComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ]
})
export class DemandeAbsenceModule { }
