import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavMenuComponent } from './nav-menu/nav-menu.component';
import { RouterModule } from '@angular/router';
import { NavMenuUserComponent } from './nav-menu-user/nav-menu-user.component';

@NgModule({
  declarations: [
    NavMenuComponent,
    NavMenuUserComponent
  ],
  imports: [
    CommonModule,
    RouterModule
  ],
  exports: [
    NavMenuComponent
  ]
})
export class SharedModule { }
