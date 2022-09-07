import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService
{
  private _user: unknown = undefined; // TODO: Type.
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
  login(role: string): Observable<boolean>
  {
    // TODO :(
    this._user = role;
    this._onAuthentication.next(role !== undefined);

    return this.onAuthentication;
  }
}
