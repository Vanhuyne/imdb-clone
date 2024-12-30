import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MovieResponse } from '../models/Movie';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  private baseUrl = 'http://localhost:8081/api/movies';
  constructor(
    private http: HttpClient
  ) { }

  getMoviesWithRandomPage(): Observable<MovieResponse> {
    const randomPage = Math.floor(Math.random() * 10) + 1;
    const apiUrl = `${this.baseUrl}?page=${randomPage}&size=10`;
    return this.http.get<MovieResponse>(apiUrl);
  }

  getMovies(page: number = 0): Observable<MovieResponse> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', '10');  // Match backend page size

    return this.http.get<MovieResponse>(this.baseUrl, { params });
  }


  
}
