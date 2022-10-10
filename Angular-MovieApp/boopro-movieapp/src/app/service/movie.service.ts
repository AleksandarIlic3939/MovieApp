import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GenericListDTO } from '../model/generic-list-dto';
import { Movie } from '../model/movie';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  private baseURL = "http://localhost:8080/api";

  constructor(private httpClient: HttpClient) { }

  getMovieListByCategory(categoryId: number, page: number, size: number, headers: HttpHeaders) : Observable<GenericListDTO<any>> {
    let params = new HttpParams;
    if (page !== null) {
      params = params.set('page', page.toString());
    }
    if (size) {
      params = params.set('size', size.toString());
    }
    return this.httpClient.get<GenericListDTO<any>>(`${this.baseURL}/user/movies?categoryId=${categoryId}&page=${page}&size=${size}`, {params, headers});
  }

  getMovieById(movieId: number, headers: HttpHeaders) : Observable<Movie> {
    return this.httpClient.get<Movie>(`${this.baseURL}/user/movies/${movieId}`, {headers});
  }

}