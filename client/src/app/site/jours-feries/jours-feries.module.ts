import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JoursFeriesComponent } from './jours-feries.component';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from 'src/app/guards/auth.guard';


const routes: Routes =
  [
    { path: 'jours-feries', component: JoursFeriesComponent, /*canActivate: [AuthGuard]*/}
  ];

@NgModule({
  declarations: [
    JoursFeriesComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ]
})
export class JoursFeriesModule { }
