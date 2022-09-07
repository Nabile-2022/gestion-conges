import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService
{
  private _user: unknown = undefined; // TODO: Type.

  constructor() { }

  get user() { return this._user; }

  is_authenticated(): boolean { return this._user !== undefined; } // TODO: Check if authed remotely.
}
