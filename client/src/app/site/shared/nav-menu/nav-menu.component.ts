import { Component, OnInit } from '@angular/core';
import { AppComponent } from 'src/app/app.component';

@Component({
  selector: 'app-nav-menu',
  templateUrl: './nav-menu.component.html',
  styleUrls: ['./nav-menu.component.css']
})
export class NavMenuComponent implements OnInit
{
  title: string;
  routes =
  [
    { path: '', name: 'Accueil' },
    { path: 'gestion-absences', name: 'Gestion des absences' },
  ];

  constructor(private appComponent: AppComponent)
  {
    this.title = appComponent.title;
  }

  ngOnInit(): void
  {
  }
}
