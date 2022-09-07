import { Component, OnInit } from '@angular/core';
import { Role } from 'src/app/models/role';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit
{
  roles = Object.keys(Role).filter(k => isNaN(Number(k)));

  constructor() { }

  ngOnInit(): void
  {
  }
}
