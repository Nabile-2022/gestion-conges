import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { JourFerie } from '../models/jour-ferie';

const ENDPOINT = "http://localhost:3000/jours-feries";

@Injectable({
  providedIn: 'root'
})
export class JoursFeriesService {

  constructor(private http: HttpClient) { }

   /** Converts a request body's date strings back to a type. */
   private reviveDates(key: string, value: any)
   {
     return key.startsWith('date') ? new Date(value) : value;
   }

  list(): Observable<JourFerie[]>{
    return this.http.get(ENDPOINT,{ responseType: 'text' }).pipe(map(response => JSON.parse(response, this.reviveDates)));
  }
}
