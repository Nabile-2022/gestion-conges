import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Salarie } from '../models/salarie';
import { UserService } from './user.service';

const ENDPOINT = "http://localhost:21394/api/manager";
const SALARIES_ENDPOINT = ENDPOINT + '/salaries';

@Injectable({
  providedIn: 'root'
})
export class ManagerService {
  constructor(private http: HttpClient, private userService: UserService) { }

  basicAuth()
  {
    return { 'Authorization': 'Basic ' + btoa(`${this.userService.user?.email}:${this.userService.user?.email}`) };
  }

  getSalaries(): Observable<Salarie[]>
  {
    return this.http.get<Salarie[]>(SALARIES_ENDPOINT, { headers: this.basicAuth() });
  }
}
