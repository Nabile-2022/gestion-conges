import { Component, OnInit } from '@angular/core';
import { AppComponent } from 'src/app/app.component';
import { Role } from 'src/app/models/role';
import { UserService } from 'src/app/services/user.service';

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
      { path: 'validation-absences', name: 'Validation demandes', roles: [Role.Manager] },
      { path: 'jours-feries', name: 'Jours fériés' }
    ];

  constructor(appComponent: AppComponent, private userService: UserService)
  {
    this.title = appComponent.title;
  }

  ngOnInit(): void
  {
  }

  get routesForUser() { return this.routes.filter(r => !r.roles || r.roles.includes(this.userService.user?.role!))}
}
