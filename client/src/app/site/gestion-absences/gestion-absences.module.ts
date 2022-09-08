import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GestionAbsencesComponent } from './gestion-absences.component';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes =
  [
    { path: 'gestion-absences', component: GestionAbsencesComponent }
  ];

@NgModule({
  declarations: [
    GestionAbsencesComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ]
})
export class GestionAbsencesModule { }
