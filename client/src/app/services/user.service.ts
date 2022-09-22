import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Role } from '../models/role';
import { Salarie } from '../models/salarie';

@Injectable({
  providedIn: 'root'
})
export class UserService
{
  private _user?: Salarie;
  private _onAuthentication = new BehaviorSubject(false);

  constructor() { }

  get user() { return this._user; }

  /**
   * Event returning whether the user managed to authenticate.
   * AppComponent uses this for hiding NavMenuComponent while logged out.
   */
  get onAuthentication(): Observable<boolean> { return this._onAuthentication.asObservable(); }

  is_authenticated(): boolean { return this._user !== undefined; } // TODO: Check if authed remotely.

  /**
   * Logs a user into the service.
   * @param role The role to assume.
   */
  login(role: Role): Observable<boolean>
  {
    // TODO :(
    this._user =
    {
      nom: 'Nom',
      prenom: 'Prénom',
      email: 'e@mail.org',
      role: role,
      absences:[]
    };
    this._onAuthentication.next(this._user !== undefined);

    return this.onAuthentication;
  }

  logout(): Observable<boolean>
  {
    this._user = undefined;
    this._onAuthentication.next(this._user !== undefined);

    return this.onAuthentication;
  }
}
