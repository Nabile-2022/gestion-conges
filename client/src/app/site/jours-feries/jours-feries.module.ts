import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JoursFeriesComponent } from './jours-feries.component';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthGuard } from 'src/app/guards/auth.guard';
import { DeleteModalComponent } from './delete-modal/delete-modal.component';


const routes: Routes =
  [
    { path: 'jours-feries', component: JoursFeriesComponent, canActivate: [AuthGuard]}
  ];

@NgModule({
  declarations: [
    JoursFeriesComponent,
    DeleteModalComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(routes)
  ]
})
export class JoursFeriesModule { }
