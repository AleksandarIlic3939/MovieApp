import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class JwtClientService {

  private baseURL = 'http://localhost:8080/api';

  constructor(private httpClient: HttpClient) { }

  public generateToken(request: any) {
    return this.httpClient.post(`${this.baseURL}/login`, request, {responseType: 'text' as 'json'});
  }

  public saveUser(token: any, path: string, user: User) {
    let tokenStr = 'Bearer ' + token;
    const headers = new HttpHeaders().set('Content-Type', 'application/json').set('Authorization', tokenStr);
    return this.httpClient.post(this.baseURL + path, JSON.stringify(user), {headers, responseType: 'text' as 'json'});
  }

}