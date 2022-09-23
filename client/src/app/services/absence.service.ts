import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { Absence } from '../models/absence';
import { CompteurAbsences } from '../models/compteur-absences';
import { UserService } from './user.service';

const ENDPOINT = "http://localhost:21394/api/absences";
const ENDPOINT_COMPTEUR = ENDPOINT + '/compteur';

@Injectable({
  providedIn: 'root'
})
export class AbsenceService
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

  list(): Observable<Absence[]>
  {
    return this.http.get(ENDPOINT, { responseType: 'text', headers: this.basicAuth() }).pipe(map(response => JSON.parse(response, this.reviveDates)));
  }

  delete(absence: Absence): Observable<any>
  {
    return this.http.delete(`${ENDPOINT}/${absence.id}`, { headers: this.basicAuth() });
  }

  getCompteur(): Observable<CompteurAbsences>
  {
    return this.http.get<CompteurAbsences>(ENDPOINT_COMPTEUR, { headers: this.basicAuth() });
  }

  addAbsence(absence: Absence): Observable<Absence>
  {
    return this.http.post<Absence>(ENDPOINT, absence, { headers: this.basicAuth() });
  }

  updateAbsence(absence: Absence): Observable<Absence>
  {
    return this.http.put<Absence>(`${ENDPOINT}/${absence.id}`, absence, { headers: this.basicAuth() });
  }

  validate(absence: Absence): Observable<Absence>
  {
    return this.http.put<Absence>(`${ENDPOINT}/validate/${absence.id}`, null, { headers: this.basicAuth() });
  }

  reject(absence: Absence): Observable<Absence>
  {
    return this.http.delete<Absence>(`${ENDPOINT}/reject/${absence.id}`, { headers: this.basicAuth() });
  }
}
