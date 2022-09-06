import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent
{
  title = 'Gestion des congés';
  user: unknown = undefined; // TODO: Type, service. This should be moved to the user service altogether !
}
