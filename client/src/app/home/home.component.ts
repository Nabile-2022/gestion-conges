import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit
{
  constructor(private app: AppComponent, private router: Router)
  {
    this.authenticate();
  }

  ngOnInit(): void
  {
  }

  /**
   * Redirects to the login page if the user isn't authenticated.
   */
  private authenticate()
  {
    if (this.app.user !== undefined) // TODO: Check if authed remotely. Move the full check to a service method returning a boolean.
      return;

    this.router.navigate(['login']);
  }
}
