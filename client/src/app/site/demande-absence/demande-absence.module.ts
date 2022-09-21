import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DemandeAbsenceComponent } from './demande-absence.component';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthGuard } from 'src/app/guards/auth.guard';

const routes: Routes =
[
  { path: 'gestion-absences/demande', component: DemandeAbsenceComponent, canActivate: [AuthGuard]  },
  { path: 'gestion-absences/modifier', component: DemandeAbsenceComponent, canActivate: [AuthGuard]  },
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
