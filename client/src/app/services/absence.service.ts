import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { Absence } from '../models/absence';
import { CompteurAbsences } from '../models/compteur-absences';

const ENDPOINT = "http://localhost:3000/absences";
const ENDPOINT_COMPTEUR = ENDPOINT + '/compteur';

@Injectable({
  providedIn: 'root'
})
export class AbsenceService
{
  constructor(private http: HttpClient) { }

  /** Converts a request body's date strings back to a type. */
  private reviveDates(key: string, value: any)
  {
    return key.startsWith('date') ? new Date(value) : value;
  }

  list(): Observable<Absence[]>
  {
    return this.http.get(ENDPOINT, { responseType: 'text' }).pipe(map(response => JSON.parse(response, this.reviveDates)));
  }

  getCompteur(): Observable<CompteurAbsences>
  {
    return this.http.get<CompteurAbsences>(ENDPOINT_COMPTEUR);
  }
}