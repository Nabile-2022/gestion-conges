import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GestionAbsencesComponent } from './gestion-absences.component';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from 'src/app/guards/auth.guard';

const routes: Routes =
  [
    { path: 'gestion-absences', component: GestionAbsencesComponent, canActivate: [AuthGuard] }
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
