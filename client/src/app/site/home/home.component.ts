import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit
{
  constructor(private userService: UserService, private router: Router)
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
    if (!this.userService.is_authenticated())
      this.router.navigate(['login']);
  }
}
