import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { JourFerie } from '../models/jour-ferie';
import { UserService } from './user.service';

const ENDPOINT = "http://localhost:21394/api/jours-feries";

@Injectable({
  providedIn: 'root'
})
export class JoursFeriesService
{

  constructor(private http: HttpClient, private userService: UserService) { }

  basicAuth()
  {
    return { 'Authorization': 'Basic ' + btoa(`${this.userService.user?.email}:${this.userService.user?.email}`) };
  }

  /** Converts a request body's date strings back to a type. */
  private reviveDates(key: string, value: any)
  {
    return key.startsWith('date') ? new Date(value) : value;
  }

  list(): Observable<JourFerie[]>
  {
    return this.http.get(ENDPOINT, { responseType: 'text', headers: this.basicAuth() }).pipe(map(response => JSON.parse(response, this.reviveDates)));
  }

  delete(jourFerie: JourFerie): Observable<any>
  {
    return this.http.delete(`${ENDPOINT}/${jourFerie.id}`, { headers: this.basicAuth() });
  }
}
