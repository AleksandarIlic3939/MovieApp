import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from '../model/category';
import { GenericListDTO } from '../model/generic-list-dto';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private baseURL = "http://localhost:8080/api";

  constructor(private httpClient: HttpClient) { }
  
  getCategoryList(page: number, size: number, headers: HttpHeaders) : Observable<GenericListDTO<any>> {
    let params = new HttpParams;
    if (page !== null) {
      params = params.set('page', page.toString());
    }
    if (size) {
      params = params.set('size', size.toString());
    }
    return this.httpClient.get<GenericListDTO<any>>(`${this.baseURL}/user/categories?page=${page}&size=${size}`, {params, headers});
  }

}