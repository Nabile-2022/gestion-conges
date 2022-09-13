import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-nav-menu-user',
  templateUrl: './nav-menu-user.component.html',
  styleUrls: ['./nav-menu-user.component.css']
})
export class NavMenuUserComponent implements OnInit
{
  constructor(public userService: UserService, private router: Router) { }

  ngOnInit(): void
  {
  }

  logout()
  {
    this.userService.logout().subscribe(authenticated =>
    {
      if (!authenticated)
        this.router.navigate(['login']);
    });
  }
}
