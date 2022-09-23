import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Role } from 'src/app/models/role';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit
{
  form: FormGroup;
  roles = Object.values(Role).filter(k => isNaN(Number(k)));

  constructor(private userService: UserService, private formBuilder: FormBuilder, private router: Router)
  {
    this.form = formBuilder.group(
      {
        role: [Role.Salarie]
      }
    );
  }

  ngOnInit(): void
  {
  }

  login()
  {
    const role = this.form.controls['role'].value;

    this.userService.login(role).subscribe(authenticated =>
    {
      if (authenticated)
        this.router.navigate(['gestion-absences']);
    });
  }
}
